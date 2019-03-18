package com.blockvote.os;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blockvote.os.Commons.APPDATA_PATH;
import static com.blockvote.os.Commons.DEFAULT_PORT;
import static com.blockvote.os.Commons.GENESIS_PATH;
import static com.blockvote.os.Commons.GETH_PATH;
import static com.blockvote.os.Commons.KEYSTORE_PATH;
import static com.blockvote.os.Commons.NETWORK_ID;
import static com.blockvote.os.Commons.NODE_PATH;
import static com.blockvote.os.Commons.RPC_PORT;
import static java.lang.String.valueOf;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.io.FileUtils.deleteDirectory;

public class UnixInteraction implements OsInteraction {

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
