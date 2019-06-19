package com.blockvote.voter.controllers;

import com.blockvote.core.gethRpcServices.MiningService;
import com.blockvote.core.observer.LogoutObservable;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static javafx.application.Platform.runLater;

public class MainPageController extends LogoutObservable {

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
            supplyAsync(() -> {
                try {
                    runLater(() -> miningToggle.setDisable(true));
                    return miningService.startMining();
                } catch (UnirestException e) {
                    e.printStackTrace();
                    runLater(() -> miningToggle.setDisable(false));
                    throw new RuntimeException("Failed to connect to the node.");
                }
            })
                    .thenAccept(jsonNodeHttpResponse -> {
                        runLater(() -> {
                            miningToggle.setStyle("-fx-background-color: #85eca5;");
                            miningToggle.setDisable(false);
                            miningToggle.setText("ON");

                        });
                    })
                    .exceptionally(ex -> {
                        miningToggle.setDisable(false);
                        ex.printStackTrace();
                        return null;
                    });
        } else {
            //  stop mining
            supplyAsync(() -> {
                try {
                    runLater(() -> miningToggle.setDisable(true));
                    return miningService.stopMinig();
                } catch (UnirestException e) {
                    e.printStackTrace();
                    runLater(() -> miningToggle.setDisable(false));
                    throw new RuntimeException("Failed to connect to the node.");
                }
            })
                    .thenAccept(jsonNodeHttpResponse -> {
                        miningToggle.setDisable(false);
                        miningToggle.setStyle("-fx-background-color: #ff6060;");
                        runLater(() -> miningToggle.setText("OFF"));
                    })
                    .exceptionally(ex -> {
                        miningToggle.setDisable(false);
                        ex.printStackTrace();
                        return null;
                    });
        }
    }
}
