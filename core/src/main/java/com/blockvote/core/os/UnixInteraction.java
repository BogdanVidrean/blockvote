package com.blockvote.core.os;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.blockvote.core.os.Commons.GENESIS_DISK_LOCATION;
import static com.blockvote.core.os.Commons.GETH_DISK_LOCATION;
import static com.blockvote.core.os.Commons.KEYSTORE_PATH;
import static com.blockvote.core.os.Commons.NETWORK_ID;
import static com.blockvote.core.os.Commons.NODE_PATH;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.setPosixFilePermissions;
import static java.nio.file.Paths.get;
import static java.nio.file.attribute.PosixFilePermissions.fromString;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteDirectory;

public class UnixInteraction implements OsInteraction {

    public static final int GETH_TIMEOUT_SECONDS = 10;
    private static final Logger log = LogManager.getLogger(UnixInteraction.class);

    @Override
    public int startLocalNode() throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        String[] gethLaunchArgs = new String[]{"./start_geth_unix.sh", valueOf(NETWORK_ID), valueOf(getAvailablePort()),
                valueOf(RPC_PORT)};
        builder.command(gethLaunchArgs);
        builder.directory(get(GETH_DISK_LOCATION).toFile());

        //start Go Ethereum and collect its PID in order to have a reference from Blockvote to kill it
        Process goEthereumStartingScript = builder.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(goEthereumStartingScript.getInputStream()));
        String line = bufferedReader.readLine();

        if (line == null) {
            log.error("Failed to read the PID of Go Ethereum process.");
            throw new IOException("Failed to read the PID of Go Ethereum process.");
        }

        return parseInt(line);
    }

    private int getAvailablePort() throws IOException {
        try (
                ServerSocket socket = new ServerSocket(0)
        ) {
            return socket.getLocalPort();
        }
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
            process.waitFor(GETH_TIMEOUT_SECONDS, SECONDS);
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
            InputStream source = getClass().getResourceAsStream("/geth_client/mac_os/geth");
            Path destination = get(GETH_DISK_LOCATION, "geth");
            copyInputStreamToFile(source, destination.toFile());

            Set<PosixFilePermission> permissions = fromString("rwxr-xr-x");
            setPosixFilePermissions(get(destination.toUri()), permissions);

            // copy the starting script for Go Ethereum as well
            source = getClass().getResourceAsStream("/start_geth_unix.sh");
            destination = get(GETH_DISK_LOCATION, "start_geth_unix.sh");
            copyInputStreamToFile(source, destination.toFile());
            setPosixFilePermissions(get(destination.toUri()), permissions);
        } catch (IOException e) {
            log.error("Failed to copy Go Ethereum to disk.", e);
        }
    }

    @Override
    public boolean checkIfGethIsAvailable() {
        try {
            String[] args = new String[]{"./geth", "version"};
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(args);
            builder.directory(get(GETH_DISK_LOCATION).toFile());

            Process process = builder.start();
            process.waitFor(GETH_TIMEOUT_SECONDS, SECONDS);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), UTF_8));
            String titleLine = bufferedReader.readLine();
            String versionLine = bufferedReader.readLine();
            process.destroy();
            return StringUtils.equals(titleLine, "Geth") && StringUtils.equals(versionLine, "Version: 1.8.27-stable");
        } catch (IOException | InterruptedException e) {
            log.error("Failed to check for Go Ethereum version.", e);
            return false;
        }
    }
}
