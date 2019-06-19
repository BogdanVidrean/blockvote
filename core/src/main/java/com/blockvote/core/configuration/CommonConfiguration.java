package com.blockvote.core.configuration;

import com.blockvote.core.bootstrap.BootstrapService;
import com.blockvote.core.contracts.dispatcher.ElectionsDispatcher;
import com.blockvote.core.contracts.dispatcher.ElectionsDispatcherImpl;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.contracts.proxy.ElectionMasterProxy;
import com.blockvote.core.gethRpcServices.AdminService;
import com.blockvote.core.gethRpcServices.MiningService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

import static com.blockvote.core.os.Commons.BLOCKVOTE_PROPERTIES_LOCATION;
import static com.blockvote.core.os.Commons.RPC_HOST;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static com.blockvote.core.os.Commons.RPC_PROTOCOL;
import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.web3j.protocol.Web3j.build;

@Configuration
@SuppressWarnings("SpringFacetCodeInspection")
public class CommonConfiguration {

    @Bean
    public HttpService httpService() {
        String rpcUrlBuilder = RPC_PROTOCOL + "://" + RPC_HOST + ":" + RPC_PORT;
        return new HttpService(rpcUrlBuilder);
    }

    @Bean
    public Web3j web3j() {
        return build(httpService());
    }

    @Bean
    public IElectionMaster electionsMaster() {
        ElectionMasterProxy electionMasterProxy = new ElectionMasterProxy();
        electionMasterProxy.setWeb3j(web3j());
        electionMasterProxy.setApplicationProperties(applicationProperties());
        return electionMasterProxy;
    }

    @Bean
    public ElectionsDispatcher electionsDispatcher() {
        ElectionsDispatcherImpl electionsDispatcher = new ElectionsDispatcherImpl();
        electionsDispatcher.setWeb3j(web3j());
        return electionsDispatcher;
    }

    @Bean
    public MiningService miningService() {
        return new MiningService();
    }

    @Bean
    public AdminService adminService() {
        return new AdminService();
    }

    @Bean
    public BootstrapService bootstrapService() {
        return new BootstrapService(applicationProperties());
    }

    @Bean
    public Properties applicationProperties() {
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(new FileInputStream(BLOCKVOTE_PROPERTIES_LOCATION));
        } catch (IOException e) {
            try {
                InputStream source = getClass().getResourceAsStream("/blockvote.properties");
                Path destination = get(BLOCKVOTE_PROPERTIES_LOCATION);
                copyInputStreamToFile(source, destination.toFile());
                applicationProperties.load(new FileInputStream(BLOCKVOTE_PROPERTIES_LOCATION));
            } catch (IOException copyingException) {
                e.printStackTrace();
            }
        }
        return applicationProperties;
    }
}
