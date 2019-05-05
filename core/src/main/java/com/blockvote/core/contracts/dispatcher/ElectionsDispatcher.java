package com.blockvote.core.contracts.dispatcher;

import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.observer.LoginObserver;
import org.web3j.protocol.Web3j;

public interface ElectionsDispatcher extends LoginObserver {
    IElection getElection(String address);

    void setWeb3j(Web3j web3j);
}
