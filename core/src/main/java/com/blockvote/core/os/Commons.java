package com.blockvote.core.os;

import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;

@SuppressWarnings("WeakerAccess")
public abstract class Commons {
    public static final int NETWORK_ID = 8956;
    public static final int DEFAULT_PORT = 30307;
    public static final int RPC_PORT = 8507;
    public static final int CHAIN_ID = 3792;
    public static final String RPC_PROTOCOL = "http";
    public static final String RPC_HOST = "localhost";
    public static final String DEFAULT_BOOTSTRAP_SERVER_URL = "http://localhost:8080";
    public static final String MASTER_CONTRACT_ADDRESS = "0x368c2e11fd0201f454eb713e7537d9ec54784990";
    //    Master Contract with no elections added
//    public static final String MASTER_CONTRACT_ADDRESS = "0xe8a1038e3d10ae81f7df2f250240183fb296d545";
    public static final String APPDATA_APPLICATION_FOLDER_NAME = "blockvote";
    public static final String APPDATA_PATH = get(getProperty("user.home"), "Library", "Application Support",
            APPDATA_APPLICATION_FOLDER_NAME)
            .toAbsolutePath()
            .toString();
    public static final String NODE_PATH = get(APPDATA_PATH, "node")
            .toAbsolutePath().toString();
    public static final String KEYSTORE_PATH = get(NODE_PATH, "keystore")
            .toAbsolutePath().toString();
    public static final String GETH_DISK_LOCATION = get(APPDATA_PATH, "geth_client")
            .toAbsolutePath().toString();
    public static final String GENESIS_DISK_LOCATION = get(APPDATA_PATH, "genesis.json")
            .toAbsolutePath().toString();
    public static final String BLOCKVOTE_PROPERTIES_LOCATION = get(APPDATA_PATH, "blockvote.properties")
            .toAbsolutePath().toString();
}
