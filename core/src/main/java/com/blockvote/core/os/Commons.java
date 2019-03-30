package com.blockvote.core.os;

import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;

@SuppressWarnings("WeakerAccess")
public abstract class Commons {
    public static final int NETWORK_ID = 8956;
    public static final int DEFAULT_PORT = 30307;
    public static final int RPC_PORT = 8507;
    public static final int CHAIN_ID = 3792;
    public static final String BOOTNODE =
            "enode://cc7065603752954b9848d6b6501edf7d41773450661abd95be877e0e34f35659cdabfe35154a" +
                    "a81eb279726a13f6f513932f63f4cae2d5f30e25f0e811df17ab@100.114.25.143:30308";
    //    public static final String CONTRACT_ADDRESS = "0x8fb55924b14b1aed46b952310c212a6a173a6a1b";
    //  Derp test with address book or sth like that
    public static final String CONTRACT_ADDRESS = "0xaf50418b534f3af879fea93b5a1f115a0156a8ee";
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
    public static final String GETH_DISK_LOCATION = get(APPDATA_PATH, "geth_client")
            .toAbsolutePath().toString();
    public static final String GENESIS_DISK_LOCATION = get(APPDATA_PATH, "genesis.json")
            .toAbsolutePath().toString();
}