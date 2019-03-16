package com.blockvote.controllers;

import com.blockvote.os.OsInteraction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.blockvote.os.Commons.CHAIN_ID;
import static java.lang.Long.parseLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static javafx.collections.FXCollections.observableArrayList;

public class AppPreloaderController {

    @FXML
    private Button logInButton;
    @FXML
    private ListView<String> accountsListView;
    @FXML
    private Button createNewAccButton;

    private OsInteraction osInteraction;
    private Map<String, File> accountFilesMap = new HashMap<>();

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
        List<File> accounts = osInteraction.loadAvailableAccounts();
        if (accounts.size() != 0) {
            accountFilesMap.putAll(accounts.stream().collect(toMap(f -> formatAddressFromKeystoreFileName(f.getName()),
                    identity())));
            accountsListView.setVisible(true);
            accountsListView.setItems(observableArrayList(accounts.stream().map(f -> {
                return formatAddressFromKeystoreFileName(f.getName());
            }).collect(toList())));
        } else {
            createNewAccButton.setVisible(true);
        }
    }

    private String formatAddressFromKeystoreFileName(String fileName) {
        String[] parts = fileName.split("--");
        return (parts[parts.length - 1]);
    }

    @FXML
    public void selectAddressToLogIn(MouseEvent mouseEvent) {
        String selectedAddress = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAddress != null) {
            logInButton.setDisable(false);
        }
    }
}
