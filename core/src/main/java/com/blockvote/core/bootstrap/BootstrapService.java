package com.blockvote.core.bootstrap;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;

import java.util.Properties;

import static com.blockvote.core.os.Commons.DEFAULT_BOOTSTRAP_SERVER_URL;
import static kong.unirest.Unirest.delete;
import static kong.unirest.Unirest.get;
import static kong.unirest.Unirest.put;

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
