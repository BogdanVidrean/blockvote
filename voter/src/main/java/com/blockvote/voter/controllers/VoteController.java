package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.dispatcher.ElectionsDispatcher;
import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.observer.LoginObserver;
import com.blockvote.core.observer.LogoutObserver;
import io.reactivex.disposables.Disposable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static java.util.Collections.synchronizedList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.geometry.Pos.TOP_LEFT;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.text.Font.font;
import static org.apache.commons.lang3.tuple.Pair.of;

public class VoteController implements LoginObserver, LogoutObserver {

    private static final Logger log = LogManager.getLogger(VoteController.class);

    @FXML
    private VBox electionMasterVBox;
    @FXML
    private VBox electionsNamesContainer;

    private IElectionMaster electionMaster;
    private Label selectElectionLabel;
    private VBox noElectionsMasterPane;
    private Web3j web3j;
    private Credentials currentUserCredentials;
    private Properties applicationProperties;
    private Disposable logsDisposable;
    private Map<String, String> electionAddressesAndNames = new HashMap<>();
    private ElectionsDispatcher electionsDispatcher;
    private Label noElectionsAvailableText;
    private List<Node> currentElectionNodes = synchronizedList(new ArrayList<>());
    private Map<Integer, Pair<CheckBox, Label>> options = new HashMap<>();
    private Text userText = new Text();
    private volatile boolean areResultsVisible = false;
    private ExecutorService executorService;

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setElectionsDispatcher(ElectionsDispatcher electionsDispatcher) {
        this.electionsDispatcher = electionsDispatcher;
    }

    public void setElectionMaster(IElectionMaster electionMaster) {
        this.electionMaster = electionMaster;
    }

    @FXML
    public void initialize() {
        //  user text
        VBox.setMargin(userText, new Insets(20, 0, 0, 0));
        userText.setFont(font(25));
    }

    private void initNoElectionsMasterPane() {
        noElectionsMasterPane = new VBox();
        noElectionsMasterPane.setAlignment(CENTER);

        ImageView imageView = new ImageView(getClass().getResource("/images/empty_box.png").toString());
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);

        Text text1 = new Text("No election selected.");
        text1.setStyle("-fx-font-size: 30; -fx-fill: #ffffff;");
        VBox.setMargin(text1, new Insets(5, 0, 0, 0));
        noElectionsMasterPane.getChildren().addAll(asList(imageView, text1));

        electionMasterVBox.getChildren().add(noElectionsMasterPane);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateOnLogin(Credentials credentials) {
        selectElectionLabel = new Label("Select the election:");
        selectElectionLabel.setStyle("-fx-font-size: 30; -fx-text-fill: #ffffff;");
        VBox.setMargin(selectElectionLabel, new Insets(40, 0, 0, 0));

        Button refreshElectionsButton = new Button("REFRESH");
        refreshElectionsButton.setOnMousePressed(event -> {
            electionsNamesContainer.getChildren().remove(2, electionsNamesContainer.getChildren().size());
            initNoElectionsNamesPane();
            initElections();
        });

        electionsNamesContainer.getChildren().addAll(asList(selectElectionLabel, refreshElectionsButton));


        initNoElectionsMasterPane();
        initNoElectionsNamesPane();
        initElections();
        this.currentUserCredentials = credentials;
    }

    private void initNoElectionsNamesPane() {
        noElectionsAvailableText = new Label("No elections available.");
        noElectionsAvailableText.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff;");
        electionsNamesContainer.getChildren().add(noElectionsAvailableText);
    }

    @SuppressWarnings("unchecked")
    private void handleElectionSelection(final String selectedAddress) {
        IElection selectedElection = electionsDispatcher.getElection(selectedAddress);
        areResultsVisible = false;
        electionMasterVBox.getChildren().remove(noElectionsMasterPane);

        runLater(() -> {
            //  clear existing user text
            userText.setText("");

            electionMasterVBox.getChildren().removeAll(currentElectionNodes);
            currentElectionNodes = new ArrayList<>();

            //  Election Name
            HBox electionNameContainer = new HBox();
            VBox.setMargin(electionNameContainer, new Insets(30, 0, 0, 0));
            electionNameContainer.setAlignment(CENTER);
            electionNameContainer.setPrefHeight(USE_COMPUTED_SIZE);
            electionNameContainer.setPrefWidth(USE_COMPUTED_SIZE);

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

            Button voteButton = new Button("VOTE");
            VBox.setMargin(voteButton, new Insets(50, 0, 0, 0));
            voteButton.setPrefWidth(250);

            //  Election options

            selectedElection.getOptions()
                    .sendAsync()
                    .thenAcceptAsync(currentOptions -> {

                        //  Election start and end time
                        VBox electionStartAndEndTimeContainer = new VBox();
                        electionStartAndEndTimeContainer.setAlignment(CENTER);

                        IElection selectedElectionObject = electionsDispatcher.getElection(selectedAddress);
                        try {
                            long endTime = selectedElectionObject.getEndTime().send().longValue() * 1000;
                            long startTime = selectedElectionObject.getStartTime().send().longValue() * 1000;

                            // adding handler with start and end timestamp
                            voteButton.setOnMousePressed(event -> vote(selectedAddress, startTime, endTime));

                            Label startLabel = new Label("Starts at: " + new Date(startTime).toString());
                            Label endLabel = new Label("Ends at: " + new Date(endTime).toString());
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
                            log.error("Failed to create elections details.", e);
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

                        // acum adaugam butonul de vote, dupa ce am setat handler-ul cu informatiile de la election
                        currentElectionNodes.add(voteButton);

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

    private void initElections() {
        electionMaster.getElectionAddresses()
                .sendAsync()
                .thenAcceptAsync(electionsAddresses -> {
                    try {
                        List<byte[]> electionsNames = electionMaster.getElectionNames().send();

                        runLater(() -> {
                            if (electionsNames.size() > 0 && noElectionsAvailableText != null) {
                                electionsNamesContainer.getChildren().remove(noElectionsAvailableText);
                            }

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

                        });
                    } catch (Exception e) {
                        log.error("Failed to retrieve elections.", e);
                    }
                })
                .exceptionally(ex -> {
                    log.error("Failed to retrieve elections.", ex);
                    return null;
                });
    }

    private void vote(String selectedAddress, long startTime, long endTime) {
        if (validateElectionVoteTime(startTime, endTime)) {
            final AtomicInteger selectedOption = new AtomicInteger(-1);
            int selectedOptionsCounter = 0;
            for (Map.Entry<Integer, Pair<CheckBox, Label>> entry : options.entrySet()) {
                CheckBox currentOptionCheckbox = entry.getValue().getKey();
                if (currentOptionCheckbox.isSelected()) {
                    selectedOption.set(entry.getKey());
                    selectedOptionsCounter++;
                }
            }
            if (selectedOptionsCounter > 1 || selectedOptionsCounter == 0) {
                userText.setStyle("-fx-fill: #ff6060");
                userText.setText("Select one option.");
            } else {
                electionMaster.canAddressVote(currentUserCredentials.getAddress())
                        .sendAsync()
                        .thenAccept(canUserVote -> {
                            if (!canUserVote) {
                                runLater(() -> {
                                    userText.setStyle("-fx-fill: #ff6060");
                                    userText.setText("Register your address in order to vote.");
                                });
                            } else {
                                runLater(() -> {
                                    userText.setStyle("-fx-fill: #fff");
                                    userText.setText("Transaction successfully registered.");
                                });
                                IElection selectedElection = electionsDispatcher.getElection(selectedAddress);
                                selectedElection.vote(BigInteger.valueOf(selectedOption.get()))
                                        .sendAsync()
                                        .thenAccept(transactionReceipt -> {
                                            runLater(() -> {
                                                userText.setStyle("-fx-fill: #3ba53a");
                                                userText.setText("Vote successfully registered.");
                                            });
                                        })
                                        .exceptionally(ex -> {
                                            log.error("Failed to vote for the elction with the address:" + selectedAddress, ex);
                                            runLater(() -> {
                                                userText.setStyle("-fx-fill: #ff6060");
                                                userText.setText("Something went wrong, check the logs.");
                                            });
                                            return null;
                                        });
                            }
                        })
                        .exceptionally(ex -> {
                            log.error("Failed to vote for the elction with the address:" + selectedAddress, ex);
                            runLater(() -> {
                                userText.setStyle("-fx-fill: #ff6060");
                                userText.setText(ex.getCause().getMessage());
                            });
                            return null;
                        });
            }
        } else {
            userText.setStyle("-fx-fill: #ff6060");
            userText.setText("This election is not active.");
        }
    }

    private boolean validateElectionVoteTime(long startTime, long endTime) {
        long currentTimeStamp = new Date().getTime();
        return currentTimeStamp >= startTime && currentTimeStamp < endTime;
    }

    @SuppressWarnings("unchecked")
    private void getResults(String selectedAddress) {
        userText.setText("");
        if (!areResultsVisible) {
            IElection election = electionsDispatcher.getElection(selectedAddress);
            election.isElectionMarkedOver()
                    .sendAsync()
                    .thenApplyAsync(isElectionMarkedOver -> {
                        if (!isElectionMarkedOver) {
                            throw new RuntimeException("This results are not available yet.");
                        } else {
                            try {
                                return election.getResults().send();
                            } catch (Exception e) {
                                throw new RuntimeException(e.getMessage());
                            }
                        }
                    }, executorService)
                    .thenAccept(results -> {
                        AtomicInteger atomicInteger = new AtomicInteger();
                        runLater(() -> results.forEach(result -> {
                            userText.setText("");
                            areResultsVisible = true;
                            Pair<CheckBox, Label> checkBoxLabelPair = options.get(atomicInteger.getAndIncrement());
                            if (checkBoxLabelPair != null) {
                                Label label = checkBoxLabelPair.getValue();
                                label.setText(label.getText() + " - " + result + " votes");
                            }
                        }));
                    })
                    .exceptionally(ex -> {
                        log.error("Failed to vote for the election with the address: " + selectedAddress, ex);
                        runLater(() -> {
                            userText.setStyle("-fx-fill: #ff6060");
                            userText.setText(ex.getCause().getMessage());
                        });
                        return null;
                    });
        }
    }

    @Override
    public void updateOnLogout() {
        if (logsDisposable != null && !logsDisposable.isDisposed()) {
            logsDisposable.dispose();
        }
        electionMasterVBox.getChildren().remove(0, electionMasterVBox.getChildren().size());
        electionsNamesContainer.getChildren().remove(0, electionsNamesContainer.getChildren().size());
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public void setApplicationProperties(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    private String formatAddressToBeValid(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address, 0, 2);
        int index = 2;
        while (index < address.length() && address.charAt(index) == '0') {
            index++;
        }
        stringBuilder.append(address, index, address.length());
        return stringBuilder.toString();
    }

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    public void destroyOnExit() {
        if (logsDisposable != null) {
            logsDisposable.dispose();
        }
    }
}