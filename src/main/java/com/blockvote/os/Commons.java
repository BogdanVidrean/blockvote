package com.blockvote.os;

import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;

@SuppressWarnings("WeakerAccess")
public abstract class Commons {
    public static final int NETWORK_ID = 8956;
    public static final int DEFAULT_PORT = 30307;
    public static final int RPC_PORT = 8507;
    public static final int CHAIN_ID = 3792;

    public static final String APPDATA_APPLICATION_FOLDER_NAME = "blockvote";
    public static final String APPDATA_PATH = get(getProperty("user.home"), "Library", "Application Support",
            APPDATA_APPLICATION_FOLDER_NAME)
            .toAbsolutePath()
            .toString();
    public static final String NODE_PATH = get(APPDATA_PATH, "node")
            .toAbsolutePath().toString();
    public static final String GETH_PATH = get("src", "main", "resources", "geth_client")
            .toAbsolutePath().toString();
    public static final String GENESIS_PATH = get("src", "main", "resources", "genesis.json")
            .toAbsolutePath().toString();
    public static final String KEYSTORE_PATH = get(NODE_PATH, "keystore")
            .toAbsolutePath().toString();
}
