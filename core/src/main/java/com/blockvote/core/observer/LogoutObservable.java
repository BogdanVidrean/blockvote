package com.blockvote.core.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class LogoutObservable {
    private List<LogoutObserver> logoutObserverList = new ArrayList<>();

    public void addObserver(LogoutObserver observer) {
        logoutObserverList.add(observer);
    }

    public void removeObserver(LogoutObserver observer) {
        logoutObserverList.remove(observer);
    }

    protected void notifyObservers() {
        logoutObserverList.forEach(LogoutObserver::updateOnLogout);
    }
}
