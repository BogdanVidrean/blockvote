package com.blockvote.os;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blockvote.os.Commons.DEFAULT_PORT;
import static com.blockvote.os.Commons.NETWORK_ID;
import static com.blockvote.os.Commons.RPC_PORT;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.io.FileUtils.deleteDirectory;

public class UnixInteraction implements OsInteraction {

    private static final String APPDATA_APPLICATION_FOLDER_NAME = "blockvote";
    private static final String APPDATA_PATH = get(getProperty("user.home"), "Library", "Application Support",
            APPDATA_APPLICATION_FOLDER_NAME)
            .toAbsolutePath()
            .toString();
    private static final String NODE_PATH = get(APPDATA_PATH, "node")
            .toAbsolutePath().toString();
    private static final String GETH_PATH = get("src", "main", "resources", "geth_client")
            .toAbsolutePath().toString();
    private static final String GENESIS_PATH = get("src", "main", "resources", "genesis.json")
            .toAbsolutePath().toString();
    private static final String KEYSTORE_PATH = get(NODE_PATH, "keystore")
            .toAbsolutePath().toString();

    @Override
    public Optional<Process> startLocalNode() {
        String[] args = new String[]{"./geth", "--datadir", NODE_PATH, "--networkid", valueOf(NETWORK_ID),
                "--ipcdisable", "--port", valueOf(DEFAULT_PORT), "--rpc", "--rpcapi",
                "\"eth,web3,personal,net,miner,admin,debug\"", "--rpcport", valueOf(RPC_PORT)};
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        builder.directory(get(GETH_PATH).toFile());
        try {
            return of(builder.start());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empty();
    }

    @Override
    public void createLocalNode() {
        String[] args = new String[]{"./geth", "--datadir", NODE_PATH, "init", GENESIS_PATH};
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        builder.directory(get(GETH_PATH).toFile());
        try {
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppDataFolder() {
        try {
            deleteDirectory(new File(APPDATA_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<File> loadAvailableAccounts() {
        File keystoreDirectory = get(KEYSTORE_PATH).toFile();
        if (keystoreDirectory.isDirectory()) {
            File[] files = keystoreDirectory.listFiles();
            return (files != null) ? asList(files) : new ArrayList<>();
        }
        return new ArrayList<>();
    }


}
