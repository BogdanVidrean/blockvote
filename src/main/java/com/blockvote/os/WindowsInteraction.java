package com.blockvote.os;

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
}
