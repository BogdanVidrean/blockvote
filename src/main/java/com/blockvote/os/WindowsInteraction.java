package com.blockvote.os;

import java.util.List;
import java.util.Optional;

public class WindowsInteraction implements OsInteraction {


    @Override
    public Optional<Process> startLocalNode() {
        return Optional.empty();
    }

    @Override
    public void createLocalNode() {

    }

    @Override
    public void deleteAppDataFolder() {

    }

    @Override
    public List<String> loadAvailableAccounts() {
        return null;
    }
}
