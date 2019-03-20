package com.blockvote.os;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface OsInteraction {
    String UNIX = "Unix";
    String WINDOWS = "Windows";

    Optional<Process> startLocalNode();

    void createLocalNode();

    void deleteNodeFolder();

    List<File> loadAvailableAccounts();

    void copyGethToDisk();
}
