package com.blockvote.voter.controllers;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.exceptions.BootstrapException;
import com.blockvote.core.os.OsInteraction;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.stage.Modality.APPLICATION_MODAL;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.web3j.crypto.WalletUtils.loadCredentials;

public class AppPreloaderController {

    @FXML
    private AnchorPane rootAnchorPane;
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
    private BootstrapMediator bootstrapMediator;
    private Map<String, File> accountFilesMap = new HashMap<>();

    public AppPreloaderController(OsInteraction osInteraction,
                                  Scene mainPageScene,
                                  Scene createAccountScene,
                                  CreateAccountController createAccountController,
                                  MainPageController mainPageController,
                                  BootstrapMediator bootstrapMediator) {
        this.osInteraction = osInteraction;
        this.mainPageScene = mainPageScene;
        this.createAccountScene = createAccountScene;
        this.createAccountController = createAccountController;
        this.mainPageController = mainPageController;
        this.bootstrapMediator = bootstrapMediator;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        try {
            bootstrapMediator.bootstrap();
            loadAvailableAccountWallets();
        } catch (BootstrapException e) {
            // TODO: Some retry mechanism
            e.printStackTrace();
        }
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
    public void showCreateWalletModal(MouseEvent mouseEvent) {
        Stage createAccountPasswordModal = new Stage();
        createAccountPasswordModal.initOwner(primaryStage);
        createAccountPasswordModal.initModality(APPLICATION_MODAL);
        createAccountPasswordModal.setScene(createAccountScene);
        createAccountController.setCreateAccountStage(createAccountPasswordModal);
        GaussianBlur gaussianBlur = new GaussianBlur(10000);
        rootAnchorPane.setEffect(gaussianBlur);
        createAccountPasswordModal.showAndWait();
    }
}
