package com.blockvote.core.bootstrap;

import com.blockvote.core.gethRpcServices.AdminService;
import com.blockvote.core.os.OsInteraction;
import kong.unirest.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthChainId;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static com.blockvote.core.os.Commons.CHAIN_ID;
import static java.lang.Integer.parseInt;
import static java.lang.Runtime.getRuntime;
import static java.lang.Thread.sleep;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class BootstrapMediator {

    private static final Logger log = LogManager.getLogger(BootstrapMediator.class);
    private static final CountDownLatch GETH_INIT_COUNT_DOWN_LATCH = new CountDownLatch(1);
    private final OsInteraction osInteraction;
    private final BootstrapService bootstrapService;
    private final AdminService adminService;
    private final ExecutorService executorService;
    private volatile Process gethProcess;
    private volatile String enode;
    private Web3j web3j;

    public BootstrapMediator(OsInteraction osInteraction,
                             BootstrapService bootstrapService,
                             AdminService adminService,
                             ExecutorService executorService,
                             Web3j web3j) {
        this.osInteraction = osInteraction;
        this.bootstrapService = bootstrapService;
        this.adminService = adminService;
        this.executorService = executorService;
        this.web3j = web3j;
    }

    public int bootstrap() throws IOException {
        boolean isGethAvailable = osInteraction.checkIfGethIsAvailable();
        if (!isGethAvailable) {
            osInteraction.copyGethToDisk();
        }
        int gethProcessPid = osInteraction.startLocalNode();
        if (!validateCurrentNode()) {
            recreateCorrectNode();
            gethProcessPid = osInteraction.startLocalNode();
        }
        return gethProcessPid;
    }

    private void registerCurrentNode() {
        runAsync(() -> {
            try {
                GETH_INIT_COUNT_DOWN_LATCH.await();
                if (enode != null) {
                    bootstrapService.registerNode(enode);
                }
            } catch (UnirestException | InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }, executorService)
                .exceptionally(ex -> {
                    log.error("Failed to register the current node.", ex);
                    return null;
                });
    }

    private void retrieveNodesList() {
        supplyAsync(() -> {
            try {
                GETH_INIT_COUNT_DOWN_LATCH.await();
                return bootstrapService.getNodes();
            } catch (UnirestException | InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }, executorService)
                .thenAcceptAsync(listHttpResponse -> {
                    for (Object o : listHttpResponse.getBody().getArray()) {
                        try {
                            adminService.adminAddPeer((String) o);
                        } catch (UnirestException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                }, executorService)
                .exceptionally(ex -> {
                    log.error("Failed to retrieve the list of the nodes.", ex);
                    return null;
                });
    }

    private void removeCurrentNode() {
        if (enode != null) {
            try {
                bootstrapService.removeNode(enode);
            } catch (UnirestException e) {
                log.error("Failed to remove the current node.", e);
            }
        }
    }

    private void getEnodeAddress() {
        supplyAsync(() -> {
            final int maxAttempts = 10;
            int currentAttempt = 0;
            while (currentAttempt < maxAttempts) {
                try {
                    return adminService.adminNodeInfo();
                } catch (UnirestException e) {
                    try {
                        currentAttempt--;
                        sleep(300);
                    } catch (InterruptedException ex) {
                        log.error("Failed to retrieve the current node address.", ex);
                    }
                }
            }
            throw new RuntimeException("Failed to retrieve node info.");
        }, executorService)
                .thenAcceptAsync(jsonNodeHttpResponse -> {
                    JSONObject nodeInfoNode = (JSONObject) jsonNodeHttpResponse.getBody().getObject().get("result");
                    String enode = (String) nodeInfoNode.get("enode");
                    try {
                        InetAddress ip = getFirstNonLoopbackAddress(true, false);
                        String[] splittedEnode = enode.split("@");
                        String ipAddress = ip != null ? ip.getHostAddress() : InetAddress.getLocalHost().getHostAddress();
                        this.enode = splittedEnode[0] + "@" + ipAddress + ":" + splittedEnode[1].split(":")[1];
                        GETH_INIT_COUNT_DOWN_LATCH.countDown();
                    } catch (SocketException | UnknownHostException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }, executorService)
                .exceptionally(ex -> {
                    GETH_INIT_COUNT_DOWN_LATCH.countDown();
                    log.error("Failed to retrieve the current node address.", ex);
                    return null;
                });
    }

    private boolean validateCurrentNode() throws IOException {
        final int maxAllowedAttempts = 5;
        int currentAttempt = 0;
        while (currentAttempt < maxAllowedAttempts) {
            try {
                sleep(300);
                final EthChainId ethChainId = web3j.ethChainId().send();
                return ethChainId != null &&
                        !ethChainId.hasError() &&
                        parseInt(ethChainId.getResult().substring(2), 16) == CHAIN_ID;
            } catch (IOException | InterruptedException e) {
                log.error("Failed to check the chain id of the Go Ethereum node. Attempt " + (currentAttempt + 1), e);
            }
            currentAttempt++;
        }
        log.error("Failed to check the chain id of the Go Ethereum node.");
        throw new IOException("Failed to validate the chain id of the node after " + maxAllowedAttempts + " attempts.");
    }

    private void recreateCorrectNode() throws IOException {
        osInteraction.deleteNodeFolder();
        osInteraction.createLocalNode();
    }

    @SuppressWarnings("SameParameterValue")
    private InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements(); ) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }

    private void addShutdownListener() {
        getRuntime().addShutdownHook(new Thread(() -> {
            if (gethProcess != null) {
                gethProcess.destroy();
            }
            removeCurrentNode();
        }));
    }

}
