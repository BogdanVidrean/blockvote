package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.impl.Election;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

import static com.blockvote.core.os.Commons.CONTRACT_ADDRESS;
import static java.math.BigInteger.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static javafx.collections.FXCollections.observableArrayList;

public class MainPageController {

    @FXML
    private PieChart resultsPieChart;
    @FXML
    private ListView<String> candidatesList;

    private ObservableList<String> candidatesObsList = observableArrayList();
    private ObservableList<PieChart.Data> results = observableArrayList();
    private Credentials credentials;
    private Web3j web3j;
    private Election election;

    @FXML
    public void initialize() {
        resultsPieChart.setData(results);
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        web3j = Web3j.build(new HttpService("http://localhost:8507"));
        election = Election.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
        try {
            List<byte[]> candidates = election.getCandidates().send();
            candidatesObsList.addAll(candidates.stream().map(String::new).collect(toList()));
            candidatesList.setItems(candidatesObsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voteForCandidate(MouseEvent mouseEvent) {
        String selectedCandidate = candidatesList.getSelectionModel().getSelectedItem();
        if (selectedCandidate != null) {
            int candidateIndex = candidatesObsList.indexOf(selectedCandidate);
            Web3j web3j = Web3j.build(new HttpService("http://localhost:8507"));
            Election election = Election.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
            try {
                election.vote(valueOf(candidateIndex)).send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void showResults(MouseEvent mouseEvent) {
        results = observableArrayList();
        resultsPieChart.setData(results);
        range(0, candidatesObsList.size()).forEach(i -> {
            try {
                BigInteger result = election.getResultsForCandidate(valueOf(i)).send();
                String candidate = candidatesObsList.get(i);
                results.add(new PieChart.Data(candidate + " - " + result.toString() + " votes", result.doubleValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
