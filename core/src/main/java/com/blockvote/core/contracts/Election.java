package com.blockvote.core.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.StaticArray2;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint8;
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
    public static final String FUNC_DERP = "derp";
    public static final String FUNC_VOTE = "vote";
    public static final String FUNC_GETRESULTSFORCANDIDATE = "getResultsForCandidate";
    private static final String BINARY = "608060405234801561001057600080fd5b50600060208181527f354a0a8593e1220e27296eaf76fd30cbbce49dad1e6baee292f01769141c7a5a8054600160ff1991821681179092557fa1eb54399709b5df020b0532dbfe29aa08896114eeb403e2437c54a0b3b5224e80548216831790557f58f92c4280883ab21cdc9deac3eeec1a9b6d90310a5ff7eb814f63fd56d4dbec80548216831790557f0920b340baebfc2bce4352edd537683779cc696575870218f83bd224bd46fca280548216831790557f2544039c8cacffcf94dcaa0891d3d857096f29dbfecc5a514a348710dea84cc280548216831790557f72630074b0cbbbefa041fbf92004cee2fffd0dc86dab2e8b1b0d44badb8f88c080548216831790557f74fd040fc9537d57f1095c332b01ac09b7df366416a078fcd9b6e62f398e9fb2805482168317905573f06bc4dd7bc1738a24c473859c391bcf81c0f0ae84527ff2ea9e3bf123ff113379d9bd23c8c9a666f1e3d22b076f42ea831a7ccbdfe90a805482168317905560408051606080820183528682527f476967656c00000000000000000000000000000000000000000000000000000082870181905291830185905284548416855560029190915560038054841685179055815190810182528381527f446f72656c00000000000000000000000000000000000000000000000000000094810185905201829052600480548216831790556005929092556006805490921617905561038490819061022e90396000f3fe608060405234801561001057600080fd5b5060043610610068577c0100000000000000000000000000000000000000000000000000000000600035046306a49fce811461006d57806318ae1352146100b0578063b3f98adc146100ce578063f939b30f146100f0575b600080fd5b610075610135565b6040518082600260200280838360005b8381101561009d578181015183820152602001610085565b5050505090500191505060405180910390f35b6100b8610187565b6040805160ff9092168252519081900360200190f35b6100ee600480360360208110156100e457600080fd5b503560ff1661018c565b005b6101106004803603602081101561010657600080fd5b503560ff166102de565b604080516fffffffffffffffffffffffffffffffff9092168252519081900360200190f35b61013d610305565b610145610305565b60005b6002811015610181576001816002811061015e57fe5b6003020160010154828260028110151561017457fe5b6020020152600101610148565b50905090565b600190565b33600090815260208190526040902054819060ff166001146101f9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260388152602001806103216038913960400191505060405180910390fd5b600160ff82166002811061020957fe5b600302016002015460ff16151561028157604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601460248201527f43616e646964617465206e6f7420666f756e642e000000000000000000000000604482015290519081900360640190fd5b5060ff16600090815260076020908152604080832080546fffffffffffffffffffffffffffffffff808216600101166fffffffffffffffffffffffffffffffff19909116179055338352908290529020805460ff19166002179055565b60ff166000908152600760205260409020546fffffffffffffffffffffffffffffffff1690565b6040805180820182529060029082908038833950919291505056fe506572736f6e206973206e6f74207265676973746572656420666f72207468697320766f7465206f7220616c726561647920766f7465642ea165627a7a723058203b798177b6a8629ada35d336a55a224cfd6e4ff81ec9610aa792a182cb7cf1930029";

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

    public RemoteCall<BigInteger> derp() {
        final Function function = new Function(FUNC_DERP,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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
