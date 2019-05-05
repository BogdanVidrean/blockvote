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
    public static final String BOOTNODE =
            "enode://cc7065603752954b9848d6b6501edf7d41773450661abd95be877e0e34f35659cdabfe35154a" +
                    "a81eb279726a13f6f513932f63f4cae2d5f30e25f0e811df17ab@100.114.25.143:30308";
    public static final String MASTER_CONTRACT_ADDRESS = "0x2f45b539f9fbbf20f21203df47f0f65d273fc998";
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
}
