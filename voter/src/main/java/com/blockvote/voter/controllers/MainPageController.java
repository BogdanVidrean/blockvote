package com.blockvote.voter.controllers;

import com.blockvote.core.contracts.Election;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.List;

import static com.blockvote.core.os.Commons.CONTRACT_ADDRESS;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

public class MainPageController {

    @FXML
    private ListView candidatesList;

    private ObservableList<String> candidatesObsList = observableArrayList();
    private Credentials credentials;

    @FXML
    public void initialize() {

    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8507"));
        Election election = Election.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
        try {
            List<byte[]> candidates = election.getCandidates().send();
            candidatesObsList.addAll(candidates.stream().map(String::new).collect(toList()));
            candidatesList.setItems(candidatesObsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
