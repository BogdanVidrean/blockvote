package com.blockvote.core.contracts.proxy;

import com.blockvote.core.contracts.impl.ElectionsMaster;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.util.List;

import static com.blockvote.core.contracts.impl.ElectionsMaster.load;
import static com.blockvote.core.os.Commons.MASTER_CONTRACT_ADDRESS;

public class ElectionMasterProxy implements IElectionMaster {
    private ElectionsMaster electionsMaster;
    private Web3j web3j;
    private Credentials credentials;

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public RemoteCall<TransactionReceipt> addElection(String electionAddress, String electionName, String organizerAddress) {
        checkIfInstanceNull();
        return electionsMaster.addElection(electionAddress, electionName, organizerAddress);
    }

    @Override
    public RemoteCall<TransactionReceipt> changeOwnerMasterAccount(String newOwnerMasterAccount) {
        checkIfInstanceNull();
        return electionsMaster.changeOwnerMasterAccount(newOwnerMasterAccount);
    }

    @Override
    public RemoteCall<TransactionReceipt> addOrganizer(String newOrganizer) {
        checkIfInstanceNull();
        return electionsMaster.addOrganizer(newOrganizer);
    }

    @Override
    public RemoteCall<List> getElectionAddresses() {
        checkIfInstanceNull();
        return electionsMaster.getElectionAddresses();
    }

    @Override
    public RemoteCall<Boolean> canAddressDeployContract(String organizerAddress) {
        checkIfInstanceNull();
        return electionsMaster.canAddressDeployContract(organizerAddress);
    }

    @Override
    public RemoteCall<TransactionReceipt> addVoter(String voterAddress) {
        checkIfInstanceNull();
        return electionsMaster.addVoter(voterAddress);
    }

    @Override
    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        checkIfInstanceNull();
        return electionsMaster.canAddressVote(votersAddress);
    }

    private void checkIfInstanceNull() {
        if (electionsMaster == null) {
            synchronized (this) {
                if (electionsMaster == null) {
                    electionsMaster = load(MASTER_CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
                }
            }
        }
    }
}
