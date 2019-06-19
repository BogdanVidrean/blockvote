package com.blockvote.core.bootstrap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Properties;

import static com.blockvote.core.os.Commons.DEFAULT_BOOTSTRAP_SERVER_URL;
import static com.mashape.unirest.http.Unirest.delete;
import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.put;

public class BootstrapService {

    private final String bootstrapServerUrl;
    private Properties applicationProperties;

    public BootstrapService(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
        bootstrapServerUrl = this.applicationProperties.getProperty("bootstrap.server.url", DEFAULT_BOOTSTRAP_SERVER_URL) + "/nodes";
    }

    public HttpResponse<String> registerNode(String enode) throws UnirestException {
        return put(bootstrapServerUrl)
                .queryString("enode", enode)
                .asString();
    }

    public HttpResponse<JsonNode> getNodes() throws UnirestException {
        return get(bootstrapServerUrl)
                .asJson();
    }

    public HttpResponse<JsonNode> removeNode(String enode) throws UnirestException {
        return delete(bootstrapServerUrl)
                .queryString("enode", enode)
                .asJson();
    }
}
