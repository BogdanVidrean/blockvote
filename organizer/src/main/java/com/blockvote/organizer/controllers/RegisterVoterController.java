package com.blockvote.organizer.controllers;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;

import static java.lang.String.format;
import static javafx.application.Platform.runLater;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class RegisterVoterController {

    private static final String TRANSACTION_FAILED_ERROR_MESSAGE = "Transaction failed with the following error: %s";
    private static final String ALL_FIELDS_MANDATORY_ERROR_MESSAGE = "Please insert the address twice.";
    private static final String ADDRESSES_NOT_MATCHING_ERROR_MESSAGE = "Addresses don't match.";
    private static final String ADDRESSES_CAN_VOTE_MESSAGE = "The address can vote.";
    private static final String ADDRESSES_CANNOT_VOTE_MESSAGE = "The address cannot vote.";
    private static final String INVALID_ADDRESS_ERROR_MESSAGE = "The address is not valid.";


    @FXML
    private TextField addressTextFieldAgain;
    @FXML
    private Text userMessage;
    @FXML
    private TextField addressTextField;

    private IElectionMaster electionMaster;

    public void setElectionMaster(IElectionMaster electionMaster) {
        this.electionMaster = electionMaster;
    }

    @FXML
    private void registerNewAccount(MouseEvent mouseEvent) {
        final String address = addressTextField.getText();
        final String addressAgain = addressTextFieldAgain.getText();
        userMessage.setStyle("-fx-fill: #ff5f5f");
        if (!isEmpty(address) && !isEmpty(addressAgain) && StringUtils.equals(address, addressAgain)) {
            electionMaster.addVoter(address)
                    .sendAsync()
                    .thenAccept(transactionReceipt -> {
                        if (transactionReceipt.isStatusOK()) {
                            userMessage.setStyle("-fx-fill: #3ba53a");
                            userMessage.setText("Voter successfully registered.");
                        } else {
                            userMessage.setText(transactionReceipt.getStatus());
                        }
                    })
                    .exceptionally(throwable -> {
                        if (throwable != null) {
                            userMessage.setText(format(TRANSACTION_FAILED_ERROR_MESSAGE, throwable.getMessage()));
                        }
                        return null;
                    });
        } else if (isEmpty(address) || isEmpty(addressAgain)) {
            userMessage.setText(ALL_FIELDS_MANDATORY_ERROR_MESSAGE);
        } else if (!StringUtils.equals(address, addressAgain)) {
            userMessage.setText(ADDRESSES_NOT_MATCHING_ERROR_MESSAGE);
        }
    }

    @FXML
    private void verifyVoterStatus(MouseEvent mouseEvent) {
        final String address = addressTextField.getText();
        final String addressAgain = addressTextFieldAgain.getText();
        userMessage.setStyle("-fx-fill: #ff5f5f");
        if (!isEmpty(address) && !isEmpty(addressAgain) && StringUtils.equals(address, addressAgain)) {
            try {
                electionMaster.canAddressVote(address)
                        .sendAsync()
                        .thenAccept(canVote -> runLater(() -> {
                            userMessage.setStyle(canVote ? "-fx-fill: #3ba53a" : "-fx-fill: #c99341");
                            userMessage.setText(canVote ? ADDRESSES_CAN_VOTE_MESSAGE : ADDRESSES_CANNOT_VOTE_MESSAGE);
                        }))
                        .exceptionally(error -> {
                            userMessage.setText("Failed to connect to the network.");
                            return null;
                        });
            } catch (NumberFormatException e) {
                userMessage.setText(INVALID_ADDRESS_ERROR_MESSAGE);
            }
        } else if (isEmpty(address) || isEmpty(addressAgain)) {
            userMessage.setText(ALL_FIELDS_MANDATORY_ERROR_MESSAGE);
        } else if (!StringUtils.equals(address, addressAgain)) {
            userMessage.setText(ADDRESSES_NOT_MATCHING_ERROR_MESSAGE);
        }
    }
}
