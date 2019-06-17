package com.blockvote.core.contracts.interfaces;

import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public interface IElection {

    RemoteCall<Boolean> canAddressVote(String votersAddress);

    RemoteCall<TransactionReceipt> vote(BigInteger optionId);

    RemoteCall<List> getOptions();

    RemoteCall<List> getResults();

    RemoteCall<TransactionReceipt> endElection();

    RemoteCall<BigInteger> getStartTime();

    RemoteCall<BigInteger> getEndTime();

    RemoteCall<Boolean> isElectionMarkedOver();
}
