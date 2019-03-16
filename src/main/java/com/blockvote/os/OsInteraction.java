package com.blockvote.os;

import java.util.Optional;

public interface OsInteraction {
    String UNIX = "Unix";
    String WINDOWS = "Windows";

    Optional<Process> startLocalNode();

    void createLocalNode();

    void deleteAppDataFolder();
}
