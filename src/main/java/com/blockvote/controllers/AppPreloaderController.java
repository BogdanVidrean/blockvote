package com.blockvote.controllers;

import com.blockvote.os.OsInteraction;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static com.blockvote.os.Commons.CHAIN_ID;
import static com.blockvote.os.OsInteraction.UNIX;
import static com.blockvote.os.OsInteractionFactory.createOsInteraction;
import static java.lang.Long.parseLong;

public class AppPreloaderController {

    @FXML
    public void initialize() {
        OsInteraction osInteraction = createOsInteraction(UNIX);
        Optional<Process> gethProcessOptional = osInteraction.startLocalNode();
        if (gethProcessOptional.isPresent()) {
            Process gethProcess = gethProcessOptional.get();
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(gethProcess.getErrorStream()));
                String line;
                boolean found = false;
                long actualChainId = 0;
                while (((line = in.readLine()) != null) && !found) {
                    if (line.contains("ChainID:")) {
                        String chainIDString = line.substring(line.indexOf("ChainID") + 8, line.indexOf("Homestead"));
                        chainIDString = chainIDString.replaceAll(" ", "");
                        actualChainId = parseLong(chainIDString);
                        found = true;
                    }
                }
                if (actualChainId != CHAIN_ID) {
                    while (gethProcess.isAlive()) {
                        gethProcess.destroy();
                    }
                    osInteraction.deleteAppDataFolder();
                    osInteraction.createLocalNode();
                    osInteraction.startLocalNode();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
