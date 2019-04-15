package com.blockvote.organizer.controllers;

import com.blockvote.core.contracts.ElectionsMaster;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import static com.blockvote.core.os.Commons.RPC_PORT;

public class MainPageController {

    @FXML
    private BorderPane borderPane;

    private Node electionCreationNode;
    private Credentials credentials;

    public void setElectionCreationNode(Node electionCreationNode) {
        this.electionCreationNode = electionCreationNode;
    }

    @FXML
    public void initialize() {

    }


    @FXML
    private void deployContract(MouseEvent mouseEvent) {
        Web3j web3j = Web3j.build(new HttpService("http://localhost:" + RPC_PORT));
        try {
            String deployedContractAddress = ElectionsMaster.deploy(web3j, credentials, new DefaultGasProvider()).send().getContractAddress();
            System.out.println("Address is: " + deployedContractAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCredentials(Credentials currentUserCredentials) {
        this.credentials = currentUserCredentials;
    }

    @FXML
    private void setElectionCreationPage(MouseEvent mouseEvent) {
        borderPane.setCenter(electionCreationNode);
    }
}
