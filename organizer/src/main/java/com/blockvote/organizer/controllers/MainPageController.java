package com.blockvote.organizer.controllers;

import com.blockvote.core.observer.LogoutObservable;
import com.blockvote.core.services.MiningService;
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
    private Node electionCreationNode;
    private Node registerVoterNode;
    private Node homeNode;
    private Node votePage;
    private Scene appPreloaderScene;
    private MiningService miningService;

    public void setMiningService(MiningService miningService) {
        this.miningService = miningService;
    }

    public void setVotePage(Node votePage) {
        this.votePage = votePage;
    }

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
        miningToggle.setStyle("-fx-background-color: #ff6060;");
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
    private void setVotePage(MouseEvent mouseEvent) {
        borderPane.setCenter(votePage);
    }

    @FXML
    private void logoutHandler(MouseEvent mouseEvent) {
        borderPane.setCenter(homeNode);
        notifyObservers();
        primaryStage.setScene(appPreloaderScene);
    }

    @FXML
    private void handleMiningToggle(MouseEvent mouseEvent) {
        if (!miningToggle.isSelected()) {
            // start mining
            supplyAsync(() -> {
                try {
                    miningToggle.setDisable(true);
                    return miningService.startMining();
                } catch (UnirestException e) {
                    e.printStackTrace();
                    miningToggle.setDisable(false);
                    throw new RuntimeException("Failed to connect to the node.");
                }
            })
                    .thenAccept(jsonNodeHttpResponse -> {
                        runLater(() -> {
                            miningToggle.setDisable(false);
                            miningToggle.setStyle("-fx-background-color: #85eca5;");
                            miningToggle.setText("ON");
                        });
                    })
                    .exceptionally(ex -> {
                        miningToggle.setDisable(false);
                        ex.printStackTrace();
                        runLater(() -> miningToggle.fire());
                        return null;
                    });
        } else {
            //  stop mining
            supplyAsync(() -> {
                try {
                    miningToggle.setDisable(true);
                    return miningService.stopMinig();
                } catch (UnirestException e) {
                    e.printStackTrace();
                    miningToggle.setDisable(false);
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
