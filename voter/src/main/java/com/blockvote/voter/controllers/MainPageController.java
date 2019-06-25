package com.blockvote.voter.controllers;

import com.blockvote.core.gethRpcServices.MiningService;
import com.blockvote.core.observer.LogoutObservable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kong.unirest.UnirestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPageController extends LogoutObservable {

    private static final Logger log = LogManager.getLogger(MainPageController.class);

    @FXML
    private ToggleButton miningToggle;
    @FXML
    private BorderPane borderPane;

    private Stage primaryStage;
    private Node homeNode;
    private Node checkEligibilityNode;
    private Node voteScene;
    private Scene appPreloaderScene;
    private MiningService miningService;

    public void setMiningService(MiningService miningService) {
        this.miningService = miningService;
    }

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
        miningToggle.setStyle("-fx-background-color: #ff6060;");
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

    @FXML
    private void handleMiningToggle(MouseEvent mouseEvent) {
        if (!miningToggle.isSelected()) {
            // start mining
            miningToggle.setDisable(true);
            try {
                miningService.startMining();
                miningToggle.setDisable(false);
                miningToggle.setStyle("-fx-background-color: #85eca5;");
                miningToggle.setText("ON");
            } catch (UnirestException e) {
                miningToggle.setDisable(false);
                miningToggle.fire();
                log.error("Failed to start mining.", e);
            }
        } else {
            //  stop mining
            miningToggle.setDisable(true);
            try {
                miningService.stopMinig();
                miningToggle.setStyle("-fx-background-color: #ff6060;");
                miningToggle.setDisable(false);
                miningToggle.setText("OFF");
            } catch (UnirestException e) {
                miningToggle.setDisable(false);
                log.error("Failed to stop mining.", e);
            }
        }
    }
}
