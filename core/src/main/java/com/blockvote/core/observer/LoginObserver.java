package com.blockvote.core.observer;

import org.web3j.crypto.Credentials;

public interface LoginObserver {
    void updateOnLogin(Credentials credentials);
}
