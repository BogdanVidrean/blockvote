package com.blockvote.voter.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Observable;
import java.util.function.Consumer;

import static com.blockvote.core.os.Commons.KEYSTORE_PATH;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.web3j.crypto.WalletUtils.generateNewWalletFile;

public class CreateAccountController extends Observable {

    private static final Logger log = LogManager.getLogger(CreateAccountController.class);

    @FXML
    private Text errorMsg;
    @FXML
    private PasswordField passwordAgainField;
    @FXML
    private PasswordField passwordField;

    private Stage createAccountStage;
    private Runnable closeCallback;
    private Consumer<String> accountCreationCallback;

    public void setCloseCallback(Runnable closeCallback) {
        this.closeCallback = closeCallback;
    }

    public void setAccountCreationCallback(Consumer<String> accountCreationCallback) {
        this.accountCreationCallback = accountCreationCallback;
    }

    public void setCreateAccountStage(Stage createAccountStage) {
        this.createAccountStage = createAccountStage;
        clearFields();
        createAccountStage.setOnCloseRequest(event -> closeCallback.run());
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void createAccount(MouseEvent mouseEvent) {
        String password = passwordField.getText();
        String passwordAgain = passwordAgainField.getText();
        if (!isEmpty(password) && !isEmpty(passwordAgain)) {
            if (password.equals(passwordAgain)) {
                try {
                    String s = generateNewWalletFile(password, get(KEYSTORE_PATH).toFile());
                    accountCreationCallback.accept(s);
                    clearFields();
                    createAccountStage.close();
                } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
                    log.error("Failed to generate new f=wallet.", e);
                }
            } else {
                errorMsg.setText("Passwords don't match.");
            }
        } else {
            errorMsg.setText("All fields are mandatory.");
        }
    }

    private void clearFields() {
        passwordField.setText("");
        passwordAgainField.setText("");
        errorMsg.setText("");
    }
}
