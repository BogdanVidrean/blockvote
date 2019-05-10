package com.blockvote.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import static com.blockvote.core.os.Commons.RPC_HOST;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static com.blockvote.core.os.Commons.RPC_PROTOCOL;
import static com.mashape.unirest.http.Unirest.post;

public class MiningService {

    private static final String GETH_RPC_URL = RPC_PROTOCOL + "://" + RPC_HOST + ":" + RPC_PORT;
    private ObjectMapper objectMapper;

    public HttpResponse<String> startMining() throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body("{\"jsonrpc\":\"2.0\",\"method\":\"miner_start\",\"params\":[],\"id\":1}")
                .asString();
    }

    public HttpResponse<JsonNode> stopMinig() throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body("{\"jsonrpc\":\"2.0\",\"method\":\"miner_stop\",\"params\":[],\"id\":1}")
                .asJson();
    }

    public HttpResponse<JsonNode> miningStatus() throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body("{\"jsonrpc\":\"2.0\",\"method\":\"eth_mining\",\"params\":[],\"id\":1}")
                .asJson();
    }
}
