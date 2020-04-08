package com.blockvote.core.os;

import java.io.File;
import java.util.List;

public class WindowsInteraction implements OsInteraction {


    @Override
    public int startLocalNode() {
        return 0;
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

    @Override
    public boolean checkIfGethIsAvailable() {
        return false;
    }
}
