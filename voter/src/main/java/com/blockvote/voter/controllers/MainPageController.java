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

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static javafx.application.Platform.runLater;

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
        if (miningToggle.isSelected()) {
            miningService.stopMinig();
            miningToggle.fire();
            miningToggle.setDisable(false);
            miningToggle.setStyle("-fx-background-color: #ff6060;");
            miningToggle.setText("OFF");
        }
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
                } catch (Throwable e) {
                    log.error("Failed to start mining.", e);
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
                        log.error("Failed to start mining.", ex);
                        runLater(() -> miningToggle.setDisable(false));
                        return null;
                    });
        } else {
            //  stop mining
            supplyAsync(() -> {
                try {
                    runLater(() -> miningToggle.setDisable(true));
                    return miningService.stopMinig();
                } catch (UnirestException e) {
                    log.error("Failed to start mining.", e);
                    runLater(() -> miningToggle.setDisable(false));
                    throw new RuntimeException("Failed to connect to the node.");
                }
            })
                    .thenAccept(jsonNodeHttpResponse -> {
                        runLater(() -> {
                            miningToggle.setDisable(false);
                            miningToggle.setStyle("-fx-background-color: #ff6060;");
                            miningToggle.setText("OFF");
                        });
                    })
                    .exceptionally(ex -> {
                        log.error("Failed to stop mining.", ex);
                        runLater(() -> miningToggle.setDisable(false));
                        return null;
                    });
        }
    }
}
