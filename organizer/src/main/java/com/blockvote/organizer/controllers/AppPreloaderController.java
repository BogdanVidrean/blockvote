package com.blockvote.organizer.controllers;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.exceptions.BootstrapException;
import com.blockvote.core.os.OsInteraction;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blockvote.core.os.Commons.RPC_PORT;
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
    private BootstrapMediator bootstrapMediator;
    private Map<String, File> accountFilesMap = new HashMap<>();

    public AppPreloaderController(OsInteraction osInteraction,
                                  Scene mainPageScene,
                                  Scene createAccountScene,
                                  CreateAccountController createAccountController,
                                  MainPageController mainPageController, BootstrapMediator bootstrapMediator) {
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
            // TODO: Some retry mechanism or sth else
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
                Admin admin = Admin.build(new HttpService("http://localhost:" + RPC_PORT));
                PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(credentials.getAddress(), password).send();
                if (personalUnlockAccount.accountUnlocked()) {
                    mainPageController.setCurrentUserCredentials(credentials);
                }
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
