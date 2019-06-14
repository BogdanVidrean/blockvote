package com.blockvote.organizer.controllers;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.observer.LogoutObserver;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static java.math.BigInteger.valueOf;
import static javafx.application.Platform.runLater;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class RegisterVoterController implements LogoutObserver {

    private static final String TRANSACTION_FAILED_ERROR_MESSAGE = "The process failed.";
    private static final String ALL_FIELDS_MANDATORY_ERROR_MESSAGE = "Please insert the address twice.";
    private static final String ADDRESSES_NOT_MATCHING_ERROR_MESSAGE = "Addresses don't match.";
    private static final String ADDRESSES_CAN_VOTE_MESSAGE = "The address can vote.";
    private static final String ADDRESSES_CANNOT_VOTE_MESSAGE = "The address cannot vote.";
    private static final String INVALID_ADDRESS_ERROR_MESSAGE = "The address is not valid.";
    private static final String MISSING_SSN_ERROR_MSG = "The ssn is missing.";

    @FXML
    private TextField ssnTextField;
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
        final String ssn = ssnTextField.getText();
        final String address = addressTextField.getText();
        final String addressAgain = addressTextFieldAgain.getText();
        userMessage.setStyle("-fx-fill: #ff5f5f");
        if (!isEmpty(address) && !isEmpty(addressAgain) && !isEmpty(ssn) && StringUtils.equals(address, addressAgain)) {
            userMessage.setStyle("-fx-fill: #ffffff");
            userMessage.setText("Transaction initiated successfully.");
            electionMaster.canSsnVote(ssn)
                    .sendAsync()
                    .thenApply(canVote -> {
                        if (canVote) {
                            throw new RuntimeException("The person is already registered.");
                        } else {
                            try {
                                return electionMaster.canAddressVote(address).send();
                            } catch (Exception e) {
                                throw new RuntimeException(TRANSACTION_FAILED_ERROR_MESSAGE);
                            }
                        }
                    })
                    .thenApply(isAddressAlreadyRegistered -> {
                        if (isAddressAlreadyRegistered) {
                            throw new RuntimeException("The address is already registered.");
                        } else {
                            try {
                                return electionMaster.getBalance().send();
                            } catch (Exception e) {
                                throw new RuntimeException(TRANSACTION_FAILED_ERROR_MESSAGE);
                            }
                        }
                    })
                    .thenAcceptAsync(masterAccountBalance -> {
                        if (masterAccountBalance.compareTo(valueOf(1000000000000000000L)) < 0) {
                            throw new RuntimeException("Not enough ether remained in master contract.");
                        } else {
                            try {
                                TransactionReceipt transactionReceipt = electionMaster.addVoter(ssn, address).send();
                                if (transactionReceipt.isStatusOK()) {
                                    runLater(() -> {
                                        userMessage.setStyle("-fx-fill: #3ba53a");
                                        userMessage.setText("Voter successfully registered.");
                                    });
                                } else {
                                    throw new RuntimeException(TRANSACTION_FAILED_ERROR_MESSAGE);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(TRANSACTION_FAILED_ERROR_MESSAGE);
                            }
                        }
                    })
                    .exceptionally(exception -> {
                        exception.printStackTrace();
                        runLater(() -> {
                            userMessage.setStyle("-fx-fill: #ff5f5f");
                            userMessage.setText(exception.getCause().getMessage());
                        });
                        return null;
                    });
        } else if (isEmpty(address) || isEmpty(addressAgain)) {
            userMessage.setText(ALL_FIELDS_MANDATORY_ERROR_MESSAGE);
        } else if (!StringUtils.equals(address, addressAgain)) {
            userMessage.setText(ADDRESSES_NOT_MATCHING_ERROR_MESSAGE);
        } else if (isEmpty(ssn)) {
            userMessage.setText("Please insert the social security number.");
        }
    }

    @FXML
    private void verifySSNStatus(MouseEvent mouseEvent) {
        final String ssn = ssnTextField.getText();
        userMessage.setStyle("-fx-fill: #ff5f5f");
        if (!isEmpty(ssn)) {
            electionMaster.canSsnVote(ssn)
                    .sendAsync()
                    .thenAccept(canVote -> runLater(() -> {
                        userMessage.setStyle(canVote ? "-fx-fill: #3ba53a" : "-fx-fill: #c98a2e");
                        userMessage.setText(canVote ? ADDRESSES_CAN_VOTE_MESSAGE : ADDRESSES_CANNOT_VOTE_MESSAGE);
                    }))
                    .exceptionally(error -> {
                        userMessage.setText(TRANSACTION_FAILED_ERROR_MESSAGE);
                        return null;
                    });
        } else if (isEmpty(ssn)) {
            userMessage.setText(MISSING_SSN_ERROR_MSG);
        }
    }

    @Override
    public void updateOnLogout() {
        ssnTextField.setText("");
        addressTextField.setText("");
        addressTextFieldAgain.setText("");
        userMessage.setText("");
    }
}
