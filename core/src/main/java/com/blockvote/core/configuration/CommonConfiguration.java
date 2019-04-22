package com.blockvote.core.configuration;

import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.contracts.proxy.ElectionMasterProxy;
import com.blockvote.core.contracts.proxy.ElectionProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import static com.blockvote.core.os.Commons.RPC_HOST;
import static com.blockvote.core.os.Commons.RPC_PORT;
import static com.blockvote.core.os.Commons.RPC_PROTOCOL;
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
    public IElection election() {
        ElectionProxy electionProxy = new ElectionProxy();
        electionProxy.setWeb3j(web3j());
        return electionProxy;
    }

    @Bean
    public IElectionMaster electionsMaster() {
        ElectionMasterProxy electionMasterProxy = new ElectionMasterProxy();
        electionMasterProxy.setWeb3j(web3j());
        return electionMasterProxy;
    }
}
