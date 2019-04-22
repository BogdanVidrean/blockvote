package com.blockvote.organizer.controllers;

import com.blockvote.core.contracts.impl.Election;
import com.blockvote.core.observer.LoginObserver;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.ArrayList;
import java.util.List;

import static com.blockvote.core.os.Commons.MASTER_CONTRACT_ADDRESS;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ElectionCreationController implements LoginObserver {

    private static final String NOT_VALID_OPTIONS_NR_PROVIDED_ERROR_MSG = "The provided value is not valid.";
    private static final String TRANSACTION_SUCCESSFULLY_SENT_MSG = "Transaction successfully registered.";
    private static final String ELECTION_CREATED_MSG = "The election was successfully created.";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button createButton;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private VBox vboxContainer;
    @FXML
    private Text userMessagge;
    @FXML
    private TextField numerOfOptionsField;

    private Credentials credentials;
    private Web3j web3j;
    private List<Node> optionsCollection = new ArrayList<>();

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    @FXML
    public void createElection(MouseEvent mouseEvent) {
        userMessagge.setText("");
        userMessagge.setStyle("-fx-fill: #fff");
        createButton.setVisible(false);
        createButton.setDisable(true);
        try {
            final int numberOfOptions = parseInt(numerOfOptionsField.getText());
            if (vboxContainer.getAlignment() == CENTER) {
                vboxContainer.setAlignment(CENTER);
            }

            Label electionNameLabel = new Label("INSERT THE NAME OF THIS ELECTION");
            electionNameLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff");
            electionNameLabel.setUnderline(true);
            vboxContainer.getChildren().add(electionNameLabel);

            TextField nameField = new TextField();
            nameField.setStyle("-fx-font-size: 20; -fx-fill: #ffffff; -fx-border-insets: 30");

            HBox nameHbox = new HBox();
            nameHbox.setAlignment(CENTER);
            nameHbox.getChildren().add(nameField);
            nameField.setAlignment(CENTER);
            vboxContainer.getChildren().add(nameHbox);


            optionsCollection = range(0, numberOfOptions)
                    .boxed()
                    .map(i -> {
                        HBox hBox = new HBox();
                        hBox.setAlignment(CENTER);

                        Label label = new Label("Option #" + (i + 1));
                        TextField textField = new TextField();

                        label.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff");
                        textField.setStyle("-fx-font-size: 20; -fx-text-fill: #ffffff; -fx-border-insets: 30");

                        hBox.getChildren().addAll(asList(label, textField));
                        return hBox;
                    })
                    .collect(toList());
            vboxContainer.getChildren().addAll(optionsCollection);

            Button doneButton = new Button("DONE");
            doneButton.setStyle("-fx-border-insets: 30;");
            doneButton.setOnMousePressed(event -> {

                if (validateElectionInfo(nameField, optionsCollection)) {
                    userMessagge.setStyle("-fx-font-size: 20;-fx-fill: #fff");
                    userMessagge.setText(TRANSACTION_SUCCESSFULLY_SENT_MSG);
                    requireNonNull(Election.deploy(
                            web3j, credentials,
                            new DefaultGasProvider(),
                            MASTER_CONTRACT_ADDRESS,
                            nameField.getText(),
                            optionsCollection.stream()
                                    .map(node -> {
                                        HBox hBox = (HBox) node;
                                        TextField textField = (TextField) hBox.getChildren().get(1);
                                        return stringToBytes32(textField.getText());
                                    })
                                    .collect(toList())))
                            .sendAsync()
                            .thenAccept(election -> {
                                userMessagge.setStyle("-fx-font-size: 20;-fx-fill: #3ba53a");
                                userMessagge.setText(ELECTION_CREATED_MSG);
                                System.out.println(election.getContractAddress());
                            })
                            .exceptionally(error -> {
                                userMessagge.setStyle("-fx-font-size: 20; -fx-fill: #ff5f5f");
                                userMessagge.setText("Something bad happened..");
                                return null;
                            });
                } else {
                    userMessagge.setText("All fields are mandatory.");
                }
            });

            Button closeButton = new Button("CLOSE");
            closeButton.setStyle("-fx-border-insets: 30;");

            HBox hBox = new HBox();
            hBox.setAlignment(CENTER);
            hBox.getChildren().add(doneButton);
            hBox.getChildren().add(closeButton);
            vboxContainer.getChildren().add(hBox);

            closeButton.setOnMousePressed(event -> {
                runLater(() -> {
                    vboxContainer.getChildren().remove(electionNameLabel);
                    vboxContainer.getChildren().remove(nameHbox);

                    vboxContainer.getChildren().removeAll(optionsCollection);
                    vboxContainer.getChildren().remove(hBox);

                    vboxContainer.setAlignment(CENTER);
                    createButton.setVisible(true);
                    createButton.setDisable(false);

                    userMessagge.setText("");
                });
            });
        } catch (NumberFormatException e) {
            userMessagge.setText(NOT_VALID_OPTIONS_NR_PROVIDED_ERROR_MSG);
        } finally {
            createButton.setVisible(true);
            createButton.setDisable(false);
        }

    }

    private boolean validateElectionInfo(TextField nameField, List<Node> optionsCollection) {
        return !isEmpty(nameField.getText()) &&
                optionsCollection.stream()
                        .reduce(true, (aBoolean, node2) -> aBoolean && !isEmpty(((TextField) (((HBox) node2).getChildren().get(1))).getText()),
                                (aBoolean, aBoolean2) -> aBoolean && aBoolean2);
    }

    private Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    @Override
    public void update(Credentials credentials) {
        this.credentials = credentials;
    }
}
