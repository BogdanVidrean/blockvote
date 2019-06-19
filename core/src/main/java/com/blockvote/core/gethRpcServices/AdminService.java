package com.blockvote.core.gethRpcServices;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import static com.blockvote.core.os.Commons.RPC_HOST;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static com.blockvote.core.os.Commons.RPC_PROTOCOL;
import static com.mashape.unirest.http.Unirest.post;

public class AdminService {

    private static final String GETH_RPC_URL = RPC_PROTOCOL + "://" + RPC_HOST + ":" + RPC_PORT;

    public HttpResponse<JsonNode> adminNodeInfo() throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body("{\"jsonrpc\":\"2.0\",\"method\":\"admin_nodeInfo\",\"params\":[],\"id\":1}")
                .asJson();
    }

    public HttpResponse<JsonNode> adminAddPeer(String peerEnode) throws UnirestException {
        return post(GETH_RPC_URL)
                .header("Content-Type", "application/json")
                .body("{\"jsonrpc\":\"2.0\",\"method\":\"admin_addPeer\",\"params\":[\"" + peerEnode + "\"],\"id\":1}")
                .asJson();
    }
}
