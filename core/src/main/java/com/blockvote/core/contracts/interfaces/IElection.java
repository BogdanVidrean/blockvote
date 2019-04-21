package com.blockvote.core.contracts.interfaces;

import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

public interface IElection {

    RemoteCall<Boolean> canAddressVote(String votersAddress);

    RemoteCall<TransactionReceipt> vote(BigInteger optionId);

    RemoteCall<List> getOptions();

    RemoteCall<BigInteger> getResultsForOption(BigInteger optionId);

}
