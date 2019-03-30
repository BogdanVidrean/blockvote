package com.blockvote.controllers;

import com.blockvote.contracts.Election;
import javafx.fxml.FXML;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import static com.blockvote.os.Commons.CONTRACT_ADDRESS;

public class MainPageController {

    Credentials credentials;

    @FXML
    public void initialize() {

    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8507"));
        Election load = Election.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
    }

}
