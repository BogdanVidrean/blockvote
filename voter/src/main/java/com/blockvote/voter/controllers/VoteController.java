package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.dispatcher.ElectionsDispatcher;
import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.observer.LoginObserver;
import com.blockvote.core.observer.LogoutObserver;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.TOP_LEFT;
import static javafx.scene.text.Font.font;
import static org.apache.commons.lang3.tuple.Pair.of;

public class VoteController implements LoginObserver, LogoutObserver {

    @FXML
    private VBox electionMasterVBox;
    @FXML
    private VBox electionsNamesContainer;

    private IElectionMaster electionMaster;
    private Label selectElectionLabel;
    private Map<String, String> electionAddressesAndNames = new HashMap<>();
    private ElectionsDispatcher electionsDispatcher;
    private List<Node> currentElectionNodes = new ArrayList<>();
    private Map<Integer, Pair<CheckBox, Label>> options = new HashMap<>();
    private Text userText = new Text();
    private volatile boolean areResultsVisible = false;

    public void setElectionsDispatcher(ElectionsDispatcher electionsDispatcher) {
        this.electionsDispatcher = electionsDispatcher;
    }

    public void setElectionMaster(IElectionMaster electionMaster) {
        this.electionMaster = electionMaster;
    }

    @FXML
    public void initialize() {
        selectElectionLabel = new Label("Select the election:");
        selectElectionLabel.setStyle("-fx-font-size: 30; -fx-text-fill: #ffffff;");
        VBox.setMargin(selectElectionLabel, new Insets(40, 0, 0, 0));
        electionsNamesContainer.getChildren().add(selectElectionLabel);

        //  user text
        VBox.setMargin(userText, new Insets(20, 0, 0, 0));
        userText.setFont(font(25));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateOnLogin(Credentials credentials) {
        electionMaster.getElectionAddresses()
                .sendAsync()
                .thenAcceptAsync(electionsAddresses -> {
                    try {
                        List<byte[]> electionsNames = electionMaster.getElectionNames().send();

                        electionsNamesContainer.getChildren().addAll(range(0, electionsAddresses.size())
                                .boxed()
                                .map(i -> {
                                    electionAddressesAndNames.put((String) electionsAddresses.get(i), new String(electionsNames.get(i)));

                                    Label newElectionLabel = new Label("#" + (i + 1) + "\t" + new String(electionsNames.get(i)));
                                    newElectionLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff;");

                                    VBox electionInformationContainer = new VBox();
                                    electionInformationContainer.setAlignment(CENTER_LEFT);
                                    electionInformationContainer.setPadding(new Insets(30, 30, 30, 30));
                                    electionInformationContainer.getChildren()
                                            .addAll(newElectionLabel);

                                    electionInformationContainer.setOnMouseEntered(event -> {
                                        electionInformationContainer.setStyle("-fx-background-color: #5bb8ff; -fx-cursor: hand");
                                    });

                                    electionInformationContainer.setOnMouseExited(event -> {
                                        if (i % 2 == 0) {
                                            electionInformationContainer.setStyle("-fx-background-color: #4CEAEB");
                                        } else {
                                            electionInformationContainer.setStyle("-fx-background-color: #39d8eb");
                                        }
                                    });
                                    if (i % 2 == 0) {
                                        electionInformationContainer.setStyle("-fx-background-color: #4CEAEB");
                                    } else {
                                        electionInformationContainer.setStyle("-fx-background-color: #39d8eb");
                                    }

                                    //  Click handler
                                    electionInformationContainer.setOnMousePressed(event -> handleElectionSelection((String) electionsAddresses.get(i)));
                                    return electionInformationContainer;
                                })
                                .collect(toList()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .exceptionally(ex -> {

                    return null;
                });
    }

    @SuppressWarnings("unchecked")
    private void handleElectionSelection(final String selectedAddress) {
        IElection selectedElection = electionsDispatcher.getElection(selectedAddress);
        areResultsVisible = false;

        runLater(() -> {
            electionMasterVBox.getChildren().removeAll(currentElectionNodes);
            currentElectionNodes = new ArrayList<>();

            //  Election Name
            HBox electionNameContainer = new HBox();
            VBox.setMargin(electionNameContainer, new Insets(30, 0, 0, 0));
            electionNameContainer.setAlignment(CENTER);

            String selectedElectionName = electionAddressesAndNames.get(selectedAddress);
            Label electionNameLabel = new Label(selectedElectionName != null ? selectedElectionName : "");
            electionNameLabel.setStyle("-fx-text-fill: #fff");
            electionNameContainer.getChildren().add(electionNameLabel);

            currentElectionNodes.add(electionNameContainer);

            //  Election address
            HBox electionAddressContainer = new HBox();
            electionAddressContainer.setAlignment(CENTER);

            Label electionAddressLabel = new Label("#" + selectedAddress);
            electionAddressLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 15");
            electionAddressContainer.getChildren().addAll(electionAddressLabel);

            currentElectionNodes.add(electionAddressContainer);

            //  Election options

            selectedElection.getOptions()
                    .sendAsync()
                    .thenAcceptAsync(currentOptions -> {

                        //  Election start and end time
                        VBox electionStartAndEndTimeContainer = new VBox();
                        electionStartAndEndTimeContainer.setAlignment(CENTER);

                        IElection selectedElectionObject = electionsDispatcher.getElection(selectedAddress);
                        try {
                            long endTime = selectedElectionObject.getStartTime().send().longValue() * 1000;
                            long startTime = selectedElectionObject.getEndTime().send().longValue() * 1000;
                            Label startLabel = new Label("Starts at: " + new Date(endTime).toString());
                            Label endLabel = new Label("Ends at: " + new Date(startTime).toString());
                            startLabel.setStyle("-fx-text-fill: #3ba53a");
                            endLabel.setStyle("-fx-text-fill: #ff5f5f");
                            electionStartAndEndTimeContainer.getChildren().add(startLabel);
                            electionStartAndEndTimeContainer.getChildren().add(endLabel);
                            currentElectionNodes.add(electionStartAndEndTimeContainer);
                            long currentTime = new Date().getTime();
                            Label statusLabel = new Label();
                            if (currentTime < startTime && currentTime > endTime) {
                                statusLabel.setText("Active");
                                statusLabel.setStyle("-fx-text-fill: #3ba53a");
                            } else if (currentTime < startTime) {
                                statusLabel.setText("Not started yet");
                                statusLabel.setStyle("-fx-text-fill: #ff5f5f");
                            } else if (currentTime > endTime) {
                                statusLabel.setText("Ended");
                                statusLabel.setStyle("-fx-text-fill: #ff5f5f");
                            }
                            currentElectionNodes.add(statusLabel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        AtomicInteger optionsCounter = new AtomicInteger();
                        currentOptions.forEach(option -> {

                            HBox currentOptionHbox = new HBox();
                            currentOptionHbox.setAlignment(TOP_LEFT);
                            CheckBox checkBox = new CheckBox();
                            VBox.setMargin(currentOptionHbox, new Insets(20, 0, 0, 40));
                            HBox.setMargin(checkBox, new Insets(0, 0, 40, 0));
                            currentOptionHbox.getChildren().add(checkBox);
                            Label currentOption = new Label(new String((byte[]) option));
                            currentOption.setStyle("-fx-text-fill: #fff;");
                            currentOptionHbox.getChildren().add(currentOption);

                            options.put(optionsCounter.getAndIncrement(), of(checkBox, currentOption));

                            checkBox.setOnSwipeLeft(event -> {

                            });

                            HBox.setMargin(currentOptionHbox, new Insets(40));

                            currentElectionNodes.add(currentOptionHbox);
                        });

                        Button button = new Button("VOTE");
                        VBox.setMargin(button, new Insets(50, 0, 0, 0));
                        button.setOnMousePressed(event -> vote(selectedAddress));
                        button.setPrefWidth(250);
                        currentElectionNodes.add(button);

                        Button resultsButton = new Button("SEE RESULTS");
                        VBox.setMargin(resultsButton, new Insets(20, 0, 0, 0));
                        resultsButton.setOnMousePressed(event -> getResults(selectedAddress));
                        resultsButton.setPrefWidth(250);
                        currentElectionNodes.add(resultsButton);

                        //User message label
                        currentElectionNodes.add(userText);

                        runLater(() -> electionMasterVBox.getChildren().addAll(currentElectionNodes));
                    })
                    .exceptionally(ex -> {

                        return null;
                    });
        });
    }

    private void vote(String selectedAddress) {
        int selectedOption = -1;
        int selectedOptionsCounter = 0;
        for (Map.Entry<Integer, Pair<CheckBox, Label>> entry : options.entrySet()) {
            CheckBox currentOptionCheckbox = entry.getValue().getKey();
            if (currentOptionCheckbox.isSelected()) {
                selectedOption = entry.getKey();
                selectedOptionsCounter++;
            }
        }
        if (selectedOptionsCounter > 1 || selectedOptionsCounter == 0) {

        } else {
            IElection selectedElection = electionsDispatcher.getElection(selectedAddress);
            selectedElection.vote(BigInteger.valueOf(selectedOption))
                    .sendAsync()
                    .thenRun(() -> runLater(() -> {
                        userText.setStyle("-fx-fill: #ff5f5f");
                        userText.setText("Transaction successfully registered.");
                    }))
                    .thenAccept(transactionReceipt -> {
                        runLater(() -> {
                            userText.setStyle("-fx-fill: #3ba53a");
                            userText.setText("Vote successfully registered.");
                        });
                    })
                    .exceptionally(ex -> {
                        runLater(() -> {
                            userText.setStyle("-fx-fill: #ff5f5f");
                            userText.setText("Something went wrong.");
                        });
                        return null;
                    });
        }
    }

    @SuppressWarnings("unchecked")
    private void getResults(String selectedAddress) {
        if (!areResultsVisible) {
            IElection election = electionsDispatcher.getElection(selectedAddress);
            AtomicInteger atomicInteger = new AtomicInteger();
            election.getResults()
                    .sendAsync()
                    .thenAccept(list -> runLater(() -> list.forEach(result -> {
                        areResultsVisible = true;
                        Pair<CheckBox, Label> checkBoxLabelPair = options.get(atomicInteger.getAndIncrement());
                        if (checkBoxLabelPair != null) {
                            Label label = checkBoxLabelPair.getValue();
                            label.setText(label.getText() + " - " + result + " votes");
                        }
                    })))
                    .exceptionally(ex -> {

                        return null;
                    });
        }
    }

    @Override
    public void updateOnLogout() {
        electionMasterVBox.getChildren().removeAll(currentElectionNodes);

    }
}