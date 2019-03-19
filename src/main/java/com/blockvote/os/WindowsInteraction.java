package com.blockvote.os;

import java.io.File;
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
    public void deleteNodeFolder() {

    }

    @Override
    public List<File> loadAvailableAccounts() {
        return null;
    }

    @Override
    public void copyGethToDisk() {

    }
}
