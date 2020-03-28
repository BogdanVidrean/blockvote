package com.blockvote.core.os;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface OsInteraction {
    String UNIX = "Unix";
    String WINDOWS = "Windows";

    Optional<Process> startLocalNode() throws IOException;

    void createLocalNode();

    void deleteNodeFolder();

    List<File> loadAvailableAccounts();

    void copyGethToDisk();
}
