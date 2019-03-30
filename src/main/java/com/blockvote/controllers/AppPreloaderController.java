package com.blockvote.controllers;

import com.blockvote.os.OsInteraction;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

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
import static javafx.stage.Modality.APPLICATION_MODAL;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.web3j.crypto.WalletUtils.loadCredentials;

public class AppPreloaderController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private ListView<String> accountsListView;
    @FXML
    private Button createNewAccButton;

    private Stage primaryStage;
    private Scene mainPageScene;
    private Scene createAccountScene;
    private OsInteraction osInteraction;
    private CreateAccountController createAccountController;
    private MainPageController mainPageController;
    private Map<String, File> accountFilesMap = new HashMap<>();

    public AppPreloaderController(OsInteraction osInteraction,
                                  Scene mainPageScene,
                                  Scene createAccountScene,
                                  CreateAccountController createAccountController,
                                  MainPageController mainPageController) {
        this.osInteraction = osInteraction;
        this.mainPageScene = mainPageScene;
        this.createAccountScene = createAccountScene;
        this.createAccountController = createAccountController;
        this.mainPageController = mainPageController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
            osInteraction.copyGethToDisk();
            gethProcessOptional = osInteraction.startLocalNode();
            if (gethProcessOptional.isPresent()) {
                Process gethProcess = gethProcessOptional.get();
                boolean isCurrentNodeValid = validateCurrentNode(gethProcess);
                if (!isCurrentNodeValid) {
                    recreateCorrectNode(gethProcess);
                }
                loadAvailableAccountWallets();
            }
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

    private void loadAvailableAccountWallets() {
        List<File> accounts = osInteraction.loadAvailableAccounts();
        if (accounts.size() != 0) {
            accountFilesMap.putAll(accounts.stream().collect(toMap(f -> formatAddressFromKeystoreFileName(f.getName()),
                    identity())));
            accountsListView.setVisible(true);
            accountsListView.setItems(observableArrayList(accounts.stream().map(f -> formatAddressFromKeystoreFileName(f.getName())).collect(toList())));
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

            passwordField.setDisable(false);
            passwordField.setVisible(true);
        }
    }

    @FXML
    public void logIn(MouseEvent mouseEvent) {
        String password = passwordField.getText();
        String selectedAddress = accountsListView.getSelectionModel().getSelectedItem();
        if (!isEmpty(password) && (selectedAddress != null)) {
            try {
                final Credentials credentials = loadCredentials(password, accountFilesMap.get(selectedAddress));
                mainPageController.setCredentials(credentials);
                primaryStage.setScene(mainPageScene);
            } catch (IOException | CipherException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void showPasswordInsertModal(MouseEvent mouseEvent) {
        Stage createAccountPasswordModal = new Stage();
        createAccountPasswordModal.initOwner(primaryStage);
        createAccountPasswordModal.initModality(APPLICATION_MODAL);
        createAccountPasswordModal.setScene(createAccountScene);
        createAccountPasswordModal.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - createAccountPasswordModal.getWidth() / 2);
        createAccountPasswordModal.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - createAccountPasswordModal.getHeight() / 2);
        createAccountController.setCreateAccountStage(createAccountPasswordModal);
        createAccountPasswordModal.showAndWait();
    }
}
