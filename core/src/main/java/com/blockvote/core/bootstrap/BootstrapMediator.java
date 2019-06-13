package com.blockvote.core.bootstrap;

import com.blockvote.core.exceptions.BootstrapException;
import com.blockvote.core.os.OsInteraction;
import com.blockvote.core.services.AdminService;
import com.blockvote.core.services.BootstrapService;
import com.mashape.unirest.http.exceptions.UnirestException;
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

import static com.blockvote.core.os.Commons.CHAIN_ID;
import static java.lang.Long.parseLong;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class BootstrapMediator {

    private final OsInteraction osInteraction;
    private final BootstrapService bootstrapService;
    private final AdminService adminService;

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
        }
        gethProcessOptional = osInteraction.startLocalNode();
        if (gethProcessOptional.isPresent()) {
            Process gethProcess = gethProcessOptional.get();
            boolean isCurrentNodeValid = validateCurrentNode(gethProcess);
            if (!isCurrentNodeValid) {
                recreateCorrectNode(gethProcess);
            }
            registerNode();
            retrieveNodesList();
            return gethProcess;
        } else {
            throw new BootstrapException();
        }
    }

    private void retrieveNodesList() {
        supplyAsync(() -> {
            try {
                return bootstrapService.getNodes();
            } catch (UnirestException e) {
                throw new RuntimeException("Failed to retrieve the nodes from the network.");
            }
        })
                .thenAcceptAsync(listHttpResponse -> {
                    for (Object o : listHttpResponse.getBody().getArray()) {
                        try {
                            adminService.adminAddPeer((String) o);
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .exceptionally(ex -> {

                    return null;
                });
    }

    private void registerNode() {
        supplyAsync(() -> {
            try {
                return adminService.adminNodeInfo();
            } catch (UnirestException e) {
                throw new RuntimeException("Failed to retrieve node info.");
            }
        })
                .thenAcceptAsync(jsonNodeHttpResponse -> {
                    JSONObject nodeInfoNode = (JSONObject) jsonNodeHttpResponse.getBody().getObject().get("result");
                    String enode = (String) nodeInfoNode.get("enode");
                    try {
                        InetAddress ip = getFirstNonLoopbackAddress(true, false);
                        String[] splittedEnode = enode.split("@");
                        String ipAddress = ip != null ? ip.getHostAddress() : InetAddress.getLocalHost().getHostAddress();
                        String enodeFinal = splittedEnode[0] + "@" + ipAddress + ":" + splittedEnode[1].split(":")[1];
                        bootstrapService.registerNode(enodeFinal);
                    } catch (UnirestException e) {
                        throw new RuntimeException("Failed to register the node to the bootstrap server.");
                    } catch (SocketException | UnknownHostException e) {
                        throw new RuntimeException("Failed to get the ip address of current host.");
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
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
            e.printStackTrace();
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
}
