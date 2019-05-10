package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CheckEligibilityController {

    @FXML
    private Text userMessage;
    @FXML
    private TextField ssnTextField;

    private IElectionMaster electionsMaster;

    public void setElectionsMaster(IElectionMaster electionsMaster) {
        this.electionsMaster = electionsMaster;
    }

    @FXML
    private void verifySSNStatus(MouseEvent mouseEvent) {
        final String ssn = ssnTextField.getText();
        userMessage.setStyle("-fx-fill: #ff5f5f");
        if (isNotEmpty(ssn)) {
            electionsMaster.canSsnVote(ssn)
                    .sendAsync()
                    .thenAccept(canVote -> {
                                if (canVote) {
                                    userMessage.setStyle("-fx-fill: #3ba53a");
                                    userMessage.setText("The address can vote.");
                                } else {

                                    userMessage.setText("The address cannot vote.");
                                }
                            }
                    )
                    .exceptionally(ex -> {

                        return null;
                    });
        } else {
            userMessage.setText("Insert the Social Security Number in the field.");
        }
    }
}
