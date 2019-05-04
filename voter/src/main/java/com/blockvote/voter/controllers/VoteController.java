package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.observer.LoginObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.web3j.crypto.Credentials;

import java.util.List;

import static java.util.stream.IntStream.range;

public class VoteController implements LoginObserver {

    private IElectionMaster electionMaster;
    @FXML
    private VBox vbox;

    private Label selectElectionLabel;

    public void setElectionMaster(IElectionMaster electionMaster) {
        this.electionMaster = electionMaster;
    }

    @FXML
    public void initialize() {
        selectElectionLabel = new Label("Select the election:");
        selectElectionLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff;");
        vbox.getChildren().add(selectElectionLabel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Credentials credentials) {
        electionMaster.getElectionAddresses()
                .sendAsync()
                .thenAcceptAsync(electionsAddresses -> {
                    try {
                        List<byte[]> electionsNames = electionMaster.getElectionNames().send();
                        range(0, electionsAddresses.size())
                                .forEach(i -> {
                                    Label newElectionLabel = new Label(new String(electionsNames.get(i)));
                                    newElectionLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff;");
                                    vbox.getChildren().add(newElectionLabel);
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                })
                .exceptionally(ex -> {

                    return null;
                });
    }
}