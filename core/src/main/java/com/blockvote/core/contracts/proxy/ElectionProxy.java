package com.blockvote.core.contracts.proxy;

import com.blockvote.core.contracts.impl.Election;
import com.blockvote.core.contracts.interfaces.IElection;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

import static com.blockvote.core.contracts.impl.Election.load;
import static com.blockvote.core.os.Commons.MASTER_CONTRACT_ADDRESS;

public class ElectionProxy implements IElection {
    private Election election;
    private Web3j web3j;
    private Credentials credentials;

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public RemoteCall<List> getCandidates() {
        checkIfInstanceNull();
        return election.getCandidates();
    }

    @Override
    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        checkIfInstanceNull();
        return election.canAddressVote(votersAddress);
    }

    @Override
    public RemoteCall<TransactionReceipt> vote(BigInteger candidateId) {
        checkIfInstanceNull();
        return election.vote(candidateId);
    }

    @Override
    public RemoteCall<BigInteger> getResultsForCandidate(BigInteger candidateId) {
        checkIfInstanceNull();
        return election.getResultsForCandidate(candidateId);
    }

    private void checkIfInstanceNull() {
        if (election == null) {
            synchronized (this) {
                if (election == null) {
                    election = load(MASTER_CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
                }
            }
        }
    }
}
