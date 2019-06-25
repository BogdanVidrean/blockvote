package com.blockvote.core.contracts.interfaces;

import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public interface IElectionMaster {

    RemoteCall<TransactionReceipt> addElection(String electionAddress, byte[] electionName, String organizerAddress);

    RemoteCall<TransactionReceipt> changeOwnerMasterAccount(String newOwnerMasterAccount);

    RemoteCall<TransactionReceipt> addOrganizer(String newOrganizer);

    RemoteCall<List> getElectionAddresses();

    RemoteCall<Boolean> canAddressDeployContract(String organizerAddress);

    RemoteCall<Boolean> canAddressVote(String votersAddress);

    RemoteCall<String> getAddressOfSocialSecurityNumber(String socialSecurityNumber);

    RemoteCall<TransactionReceipt> addVoter(String socialSecurityNumber, String voterAddress);

    RemoteCall<TransactionReceipt> removeVoter(String socialSecurityNumber);

    RemoteCall<Boolean> canSsnVote(String voterSsn);

    RemoteCall<List> getElectionNames();

    RemoteCall<BigInteger> getBalance();
}
