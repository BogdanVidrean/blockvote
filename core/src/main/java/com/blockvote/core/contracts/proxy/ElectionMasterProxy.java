package com.blockvote.core.contracts.proxy;

import com.blockvote.core.contracts.impl.ElectionsMaster;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import static com.blockvote.core.contracts.impl.ElectionsMaster.load;

public class ElectionMasterProxy implements IElectionMaster {
    private ElectionsMaster electionsMaster;
    private Web3j web3j;
    private Credentials credentials;
    private Properties applicationProperties;

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setApplicationProperties(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public RemoteCall<TransactionReceipt> addElection(String electionAddress, byte[] electionName, String organizerAddress) {
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
    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        checkIfInstanceNull();
        return electionsMaster.canAddressVote(votersAddress);
    }

    @Override
    public RemoteCall<String> getAddressOfSocialSecurityNumber(String socialSecurityNumber) {
        checkIfInstanceNull();
        return electionsMaster.getAddressOfSocialSecurityNumber(socialSecurityNumber);
    }

    @Override
    public RemoteCall<TransactionReceipt> addVoter(String socialSecurityNumber, String voterAddress) {
        checkIfInstanceNull();
        return electionsMaster.addVoter(socialSecurityNumber, voterAddress);
    }

    @Override
    public RemoteCall<TransactionReceipt> removeVoter(String socialSecurityNumber) {
        checkIfInstanceNull();
        return electionsMaster.removeVoter(socialSecurityNumber);
    }

    @Override
    public RemoteCall<Boolean> canSsnVote(String voterSsn) {
        checkIfInstanceNull();
        return electionsMaster.canSsnVote(voterSsn);
    }

    @Override
    public RemoteCall<List> getElectionNames() {
        checkIfInstanceNull();
        return electionsMaster.getElectionNames();
    }

    @Override
    public RemoteCall<BigInteger> getBalance() {
        checkIfInstanceNull();
        return electionsMaster.getBalance();
    }

    private void checkIfInstanceNull() {
        if (electionsMaster == null) {
            synchronized (this) {
                if (electionsMaster == null) {
                    String masterContractAddress = applicationProperties.getProperty("master.contract.address", "");
                    electionsMaster = load(masterContractAddress, web3j, credentials, new DefaultGasProvider());
                }
            }
        }
    }
}
