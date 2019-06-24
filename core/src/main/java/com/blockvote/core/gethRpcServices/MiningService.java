package com.blockvote.core.gethRpcServices;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;

import static com.blockvote.core.os.Commons.RPC_HOST;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static com.blockvote.core.os.Commons.RPC_PROTOCOL;
import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static kong.unirest.Unirest.post;

public class MiningService {

    private static final String GETH_RPC_URL = RPC_PROTOCOL + "://" + RPC_HOST + ":" + RPC_PORT;
    private static final int NUMBER_OF_CORES = getRuntime().availableProcessors();

    public HttpResponse<String> startMining() throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body(format("{\"jsonrpc\":\"2.0\",\"method\":\"miner_start\",\"params\":[%d],\"id\":1}", NUMBER_OF_CORES / 2))
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
