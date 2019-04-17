package com.blockvote.organizer.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class MainPageController {

    @FXML
    private BorderPane borderPane;

    private Node electionCreationNode;
    private Node registerVoterNode;
    private Node homeNode;

    public void setElectionCreationNode(Node electionCreationNode) {
        this.electionCreationNode = electionCreationNode;
    }

    public void setRegisterVoterNode(Node registerVoterNode) {
        this.registerVoterNode = registerVoterNode;
    }

    public void setHomeNode(Node homeNode) {
        this.homeNode = homeNode;
    }

    @FXML
    public void initialize() {
        borderPane.setCenter(homeNode);
    }


    @FXML
    private void setElectionCreationPage(MouseEvent mouseEvent) {
        borderPane.setCenter(electionCreationNode);
    }

    @FXML
    private void setRegisterVoterView(MouseEvent mouseEvent) {
        borderPane.setCenter(registerVoterNode);
    }

    @FXML
    private void setHomePage(MouseEvent mouseEvent) {
        borderPane.setCenter(homeNode);
    }
}
