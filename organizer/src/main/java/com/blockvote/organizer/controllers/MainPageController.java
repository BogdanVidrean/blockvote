package com.blockvote.organizer.controllers;

import com.blockvote.core.observer.LogoutObservable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainPageController extends LogoutObservable {

    @FXML
    private BorderPane borderPane;

    private Stage primaryStage;
    private Node electionCreationNode;
    private Node registerVoterNode;
    private Node homeNode;
    private Scene appPreloaderScene;

    public void setAppPreloaderScene(Scene appPreloaderScene) {
        this.appPreloaderScene = appPreloaderScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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

    @FXML
    private void logoutHandler(MouseEvent mouseEvent) {
        borderPane.setCenter(homeNode);
        notifyObservers();
        primaryStage.setScene(appPreloaderScene);
    }
}
