package com.blockvote.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.StaticArray2;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class Election extends Contract {
    public static final String FUNC_GETCANDIDATES = "getCandidates";
    public static final String FUNC_VOTE = "vote";
    public static final String FUNC_GETRESULTSFORCANDIDATE = "getResultsForCandidate";
    private static final String BINARY = "608060405234801561001057600080fd5b50600060208181527fa45f472a6c0aece853c1738456f5ad607291741d41bb7bc108fd7aa9f47196298054600160ff19918216811790925573a7fe663d49f7a8443b47bd6406f0e1440a574c1384527fe8f611e2d12f20327c2634fc2a01536ceb9cdeaa47b054df4fee1bf3f8292bf7805482168317905560408051606080820183528682527f476967656c00000000000000000000000000000000000000000000000000000082870181905291830185905284548416855560029190915560038054841685179055815190810182528381527f446f72656c0000000000000000000000000000000000000000000000000000009481018590520182905260048054821683179055600592909255600680549092161790556102f190819061013890396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806306a49fce14610046578063b3f98adc14610089578063f939b30f146100ab575b600080fd5b61004e6100e7565b6040518082600260200280838360005b8381101561007657818101518382015260200161005e565b5050505090500191505060405180910390f35b6100a96004803603602081101561009f57600080fd5b503560ff16610137565b005b6100cb600480360360208110156100c157600080fd5b503560ff16610251565b604080516001600160801b039092168252519081900360200190f35b6100ef61026f565b6100f761026f565b60005b6002811015610131576001816002811061011057fe5b600302016001015482826002811061012457fe5b60200201526001016100fa565b50905090565b33600090815260208190526040902054819060ff1660011461018d57604051600160e51b62461bcd02815260040180806020018281038252603881526020018061028e6038913960400191505060405180910390fd5b60018160ff166002811061019d57fe5b600302016002015460ff16156101fd5760408051600160e51b62461bcd02815260206004820152601460248201527f43616e646964617465206e6f7420666f756e642e000000000000000000000000604482015290519081900360640190fd5b5060ff16600090815260076020908152604080832080546001600160801b03808216600101166fffffffffffffffffffffffffffffffff19909116179055338352908290529020805460ff19166002179055565b60ff166000908152600760205260409020546001600160801b031690565b6040518060400160405280600290602082028038833950919291505056fe506572736f6e206973206e6f74207265676973746572656420666f72207468697320766f7465206f7220616c726561647920766f7465642ea165627a7a723058203cacc02c50082feb412f0a5d0ebf70c14639923b6d21a230e105c3bc1fad6af00029";

    @Deprecated
    protected Election(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Election(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Election(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Election(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static Election load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Election load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Election(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Election load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Election(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Election load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Election(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Election.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Election> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Election.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Election.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Election> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Election.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<List> getCandidates() {
        final Function function = new Function(FUNC_GETCANDIDATES,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray2<Bytes32>>() {
                }));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger candidateId) {
        final Function function = new Function(
                FUNC_VOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(candidateId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getResultsForCandidate(BigInteger candidateId) {
        final Function function = new Function(FUNC_GETRESULTSFORCANDIDATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(candidateId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }
}
