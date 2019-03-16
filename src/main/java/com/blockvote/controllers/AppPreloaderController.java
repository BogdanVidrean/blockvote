package com.blockvote.controllers;

import com.blockvote.os.OsInteraction;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import static com.blockvote.os.Commons.CHAIN_ID;
import static java.lang.Long.parseLong;
import static javafx.collections.FXCollections.observableArrayList;

public class AppPreloaderController {

    @FXML
    public ListView<String> accountsListView;

    private OsInteraction osInteraction;

    public AppPreloaderController(OsInteraction osInteraction) {
        this.osInteraction = osInteraction;
    }

    @FXML
    public void initialize() {
        Optional<Process> gethProcessOptional = osInteraction.startLocalNode();
        if (gethProcessOptional.isPresent()) {
            Process gethProcess = gethProcessOptional.get();
            boolean isCurrentNodeValid = validateCurrentNode(gethProcess);
            if (!isCurrentNodeValid) {
                recreateCorrectNode(gethProcess);
            }
            loadAvailableAccountWallets();
        } else {
            // TODO: Probably retry
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
        osInteraction.deleteAppDataFolder();
        osInteraction.createLocalNode();
        osInteraction.startLocalNode();
    }

    private void loadAvailableAccountWallets() {
        List<String> accounts = osInteraction.loadAvailableAccounts();
        accountsListView.setItems(observableArrayList(accounts));
    }
}
