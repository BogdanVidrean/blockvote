package com.blockvote.organizer.controllers;

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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javafx.geometry.Pos.CENTER;

public class ElectionCreationController {

    private static final String NOT_VALID_OPTIONS_NR_PROVIDED_ERROR_MSG = "The provided value is not valid.";

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

    private List<Node> optionsCollection = new ArrayList<>();

    @FXML
    public void createElection(MouseEvent mouseEvent) {
        userMessagge.setText("");
        createButton.setVisible(false);
        createButton.setDisable(true);
        try {
            final int numberOfOptions = parseInt(numerOfOptionsField.getText());
            if (vboxContainer.getAlignment() == CENTER) {
                vboxContainer.setAlignment(CENTER);
            }


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

            Button button = new Button("CLOSE");
            button.setStyle("-fx-border-insets: 30;");
            button.setOnMousePressed(event -> {
                vboxContainer.getChildren().removeAll(optionsCollection);
                vboxContainer.getChildren().remove(button);

                vboxContainer.setAlignment(CENTER);
                createButton.setVisible(true);
                createButton.setDisable(false);
            });

            vboxContainer.getChildren().addAll(optionsCollection);
            vboxContainer.getChildren().add(button);
        } catch (NumberFormatException e) {
            userMessagge.setText(NOT_VALID_OPTIONS_NR_PROVIDED_ERROR_MSG);
        }

    }
}
