package com.blockvote.core.observer;

import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.List;

public abstract class LoginObservable {
    private List<LoginObserver> loginObserverList = new ArrayList<>();

    public void addObserver(LoginObserver observer) {
        loginObserverList.add(observer);
    }

    public void removeObserver(LoginObserver observer) {
        loginObserverList.remove(observer);
    }

    public void notify(Credentials credentials) {
        loginObserverList.forEach(loginObserver -> loginObserver.update(credentials));
    }
}
