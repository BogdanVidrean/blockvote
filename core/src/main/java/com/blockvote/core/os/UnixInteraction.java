package com.blockvote.core.os;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.blockvote.core.os.Commons.DEFAULT_PORT;
import static com.blockvote.core.os.Commons.GENESIS_DISK_LOCATION;
import static com.blockvote.core.os.Commons.GETH_DISK_LOCATION;
import static com.blockvote.core.os.Commons.KEYSTORE_PATH;
import static com.blockvote.core.os.Commons.NETWORK_ID;
import static com.blockvote.core.os.Commons.NODE_PATH;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static java.lang.String.valueOf;
import static java.nio.file.Files.setPosixFilePermissions;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteDirectory;

public class UnixInteraction implements OsInteraction {

    private static final Logger log = LogManager.getLogger(UnixInteraction.class);

    @Override
    public Optional<Process> startLocalNode() {
        String[] args = new String[]{"./geth", "--datadir", NODE_PATH,
                "--networkid", valueOf(NETWORK_ID), "--port", valueOf(DEFAULT_PORT), "--rpc", "--rpcapi",
                "eth,web3,personal,net,miner,admin,debug", "--rpcport", valueOf(RPC_PORT), "--rpccorsdomain", "*"};
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        builder.directory(get(GETH_DISK_LOCATION).toFile());
        try {
            return of(builder.start());
        } catch (IOException e) {
            log.error("Failed to start local node.", e);
        }
        return empty();
    }

    @Override
    public void createLocalNode() {
        InputStream source = getClass().getResourceAsStream("/genesis.json");
        Path destination = get(GENESIS_DISK_LOCATION);
        try {
            copyInputStreamToFile(source, destination.toFile());
            String[] args = new String[]{"./geth", "--datadir", NODE_PATH, "init", GENESIS_DISK_LOCATION};
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(args);
            builder.directory(get(GETH_DISK_LOCATION).toFile());
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error("Failed to create local node.", e);
        }
    }

    @Override
    public void deleteNodeFolder() {
        try {
            deleteDirectory(new File(NODE_PATH));
        } catch (IOException e) {
            log.error("Failed to delete local node.", e);
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


    @Override
    public void copyGethToDisk() {
        try {
            InputStream source = getClass().getResourceAsStream("/geth_client/geth");
            Path destination = get(GETH_DISK_LOCATION, "geth");
            copyInputStreamToFile(source, destination.toFile());
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
            setPosixFilePermissions(get(destination.toUri()), permissions);
        } catch (IOException e) {
            log.error("Failed to copy geht to disk.", e);
        }
    }
}
