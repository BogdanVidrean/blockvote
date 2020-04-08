package com.blockvote.core.os;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface OsInteraction {
    String UNIX = "Unix";
    String WINDOWS = "Windows";

    int startLocalNode() throws IOException;

    void createLocalNode();

    void deleteNodeFolder();

    List<File> loadAvailableAccounts();

    void copyGethToDisk();

    boolean checkIfGethIsAvailable();
}
