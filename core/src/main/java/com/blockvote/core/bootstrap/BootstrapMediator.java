package com.blockvote.core.bootstrap;

import com.blockvote.core.exceptions.BootstrapException;
import com.blockvote.core.gethRpcServices.AdminService;
import com.blockvote.core.os.OsInteraction;
import kong.unirest.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static com.blockvote.core.os.Commons.CHAIN_ID;
import static java.lang.Long.parseLong;
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
    private volatile Process gethProcess;
    private volatile String enode;

    public BootstrapMediator(OsInteraction osInteraction,
                             BootstrapService bootstrapService,
                             AdminService adminService) {
        this.osInteraction = osInteraction;
        this.bootstrapService = bootstrapService;
        this.adminService = adminService;
    }

    public Process bootstrap() throws BootstrapException {
        Optional<Process> gethProcessOptional = osInteraction.startLocalNode();
        if (!gethProcessOptional.isPresent()) {
            osInteraction.copyGethToDisk();
            gethProcessOptional = osInteraction.startLocalNode();
        }
        if (gethProcessOptional.isPresent()) {
            Process gethProcess = gethProcessOptional.get();
            boolean isCurrentNodeValid = validateCurrentNode(gethProcess);
            if (!isCurrentNodeValid) {
                recreateCorrectNode(gethProcess);
            }
            getEnodeAddress();
            retrieveNodesList();
            registerCurrentNode();
            addShutdownListener();
            this.gethProcess = gethProcess;
            return gethProcess;
        } else {
            throw new BootstrapException();
        }
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
        })
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
        })
                .thenAcceptAsync(listHttpResponse -> {
                    for (Object o : listHttpResponse.getBody().getArray()) {
                        try {
                            adminService.adminAddPeer((String) o);
                        } catch (UnirestException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                })
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
        })
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
                })
                .exceptionally(ex -> {
                    GETH_INIT_COUNT_DOWN_LATCH.countDown();
                    log.error("Failed to retrieve the current node address.", ex);
                    return null;
                });
    }

    private boolean validateCurrentNode(Process gethProcess) {
        BufferedReader in = new BufferedReader(new InputStreamReader(gethProcess.getErrorStream()));
        String line;
        boolean found = false;
        long actualChainId = 0;
        try {
            while (((line = in.readLine()) != null) && !found) {
                if (line.contains("ChainID:")) {
                    String chainIDString = line.substring(line.indexOf("ChainID") + 8, line.indexOf("Homestead"));
                    chainIDString = chainIDString.replaceAll(" ", "");
                    actualChainId = parseLong(chainIDString);
                    found = true;
                }
                if (line.toLowerCase().contains("datadir already used by another process")) {
                    return true;
                }
            }
        } catch (IOException e) {
            log.error("Failed to validate geth client.", e);
        }
        return actualChainId == CHAIN_ID;
    }

    private void recreateCorrectNode(Process gethProcess) {
        while (gethProcess.isAlive()) {
            gethProcess.destroy();
        }
        osInteraction.deleteNodeFolder();
        osInteraction.createLocalNode();
        osInteraction.startLocalNode();
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
