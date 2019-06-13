package com.blockvote.core.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import static com.blockvote.core.os.Commons.BOOTSTRAP_SERVER_HOST;
import static com.blockvote.core.os.Commons.BOOTSTRAP_SERVER_PORT;
import static com.blockvote.core.os.Commons.BOOTSTRAP_SERVER_PROTOCOL;
import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.put;

public class BootstrapService {

    private static final String BOOTSTRAP_SERVER_URL = BOOTSTRAP_SERVER_PROTOCOL + "://" + BOOTSTRAP_SERVER_HOST +
            ":" + BOOTSTRAP_SERVER_PORT + "/nodes";

    public HttpResponse<String> registerNode(String enode) throws UnirestException {
        return put(BOOTSTRAP_SERVER_URL)
                .queryString("enode", enode)
                .asString();
    }

    public HttpResponse<JsonNode> getNodes() throws UnirestException {
        return get(BOOTSTRAP_SERVER_URL)
                .asJson();
    }
}
