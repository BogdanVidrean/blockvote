package com.blockvote.core.bootstrap;

import com.blockvote.core.exceptions.BootstrapException;
import com.blockvote.core.os.OsInteraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static com.blockvote.core.os.Commons.CHAIN_ID;
import static java.lang.Long.parseLong;

public class BootstrapMediator {

    private OsInteraction osInteraction;

    public BootstrapMediator(OsInteraction osInteraction) {
        this.osInteraction = osInteraction;
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
            return gethProcess;
        } else {
            throw new BootstrapException();
        }
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
}
