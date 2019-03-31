package com.blockvote.voter.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Observable;

import static com.blockvote.core.os.Commons.KEYSTORE_PATH;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.web3j.crypto.WalletUtils.generateNewWalletFile;

public class CreateAccountController extends Observable {

    @FXML
    private PasswordField passwordAgainField;
    @FXML
    private PasswordField passwordField;

    private Stage createAccountStage;

    public void setCreateAccountStage(Stage createAccountStage) {
        this.createAccountStage = createAccountStage;
        createAccountStage.setOnHiding(event -> {

        });
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void createAccount(MouseEvent mouseEvent) {
        String password = passwordField.getText();
        String passwordAgain = passwordAgainField.getText();
        if (!isEmpty(password) && !isEmpty(passwordAgain)) {
            if (StringUtils.equals(password, passwordAgain)) {
                try {
                    String s = generateNewWalletFile(password, get(KEYSTORE_PATH).toFile());
                    createAccountStage.close();
                } catch (CipherException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearFields() {
        passwordField.setText("");
        passwordAgainField.setText("");
    }
}
