package com.blockvote.core.contracts.interfaces;

import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

public interface IElectionMaster {

    RemoteCall<TransactionReceipt> addElection(String electionAddress, String electionName, String organizerAddress);

    RemoteCall<TransactionReceipt> changeOwnerMasterAccount(String newOwnerMasterAccount);

    RemoteCall<TransactionReceipt> addOrganizer(String newOrganizer);

    RemoteCall<List> getElectionAddresses();

    RemoteCall<Boolean> canAddressDeployContract(String organizerAddress);

    RemoteCall<TransactionReceipt> addVoter(String voterAddress);

    RemoteCall<Boolean> canAddressVote(String votersAddress);
}
