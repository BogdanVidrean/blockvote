package com.blockvote.core.contracts.dispatcher;

import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.contracts.proxy.ElectionProxy;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.util.HashMap;
import java.util.Map;

public class ElectionsDispatcherImpl implements ElectionsDispatcher {

    private Map<String, IElection> elections = new HashMap<>();
    private Credentials credentials;
    private Web3j web3j;

    @Override
    public IElection getElection(String address) {
        IElection iElection = elections.get(address);
        if (iElection != null) {
            return iElection;
        } else {
            ElectionProxy electionProxy = new ElectionProxy(address);
            electionProxy.setCredentials(credentials);
            electionProxy.setWeb3j(web3j);
            elections.put(address, electionProxy);
            return electionProxy;
        }
    }

    @Override
    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public void updateOnLogin(Credentials credentials) {
        this.credentials = credentials;
    }
}
