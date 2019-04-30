package com.blockvote.voter.controllers;

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
    private Node homeNode;
    private Node checkEligibilityNode;
    private Node voteScene;
    private Scene appPreloaderScene;

    public void setAppPreloaderScene(Scene appPreloaderScene) {
        this.appPreloaderScene = appPreloaderScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setHomeNode(Node homeNode) {
        this.homeNode = homeNode;
    }

    public void setCheckEligibilityNode(Node checkEligibilityNode) {
        this.checkEligibilityNode = checkEligibilityNode;
    }

    public void setVoteScene(Node voteScene) {
        this.voteScene = voteScene;
    }

    @FXML
    public void initialize() {
        borderPane.setCenter(homeNode);
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

    @FXML
    private void setVotePage(MouseEvent mouseEvent) {
        borderPane.setCenter(voteScene);
    }

    @FXML
    private void setCheckEligibilityPage(MouseEvent mouseEvent) {
        borderPane.setCenter(checkEligibilityNode);
    }
}
