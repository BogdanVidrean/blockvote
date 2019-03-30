package com.blockvote.contracts;

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
    public static final String FUNC_DERP = "derp";

    public static final String FUNC_GETCANDIDATES = "getCandidates";
    private static final String BINARY = "608060405234801561001057600080fd5b50738976fc040a9f2b22974e598387f40ff8e1125e7b600090815260208181527f354a0a8593e1220e27296eaf76fd30cbbce49dad1e6baee292f01769141c7a5a8054600160ff19918216811790925560408051606080820183528682527f476967656c00000000000000000000000000000000000000000000000000000082870181905291830185905284548416855560029190915560038054841685179055815190810182528381527f446f72656c00000000000000000000000000000000000000000000000000000094810185905201829052600480548216831790556005929092556006805490921617905561031f90819061011090396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806306a49fce1461005157806318ae135214610094578063b3f98adc146100b2578063f939b30f146100d4575b600080fd5b610059610110565b6040518082600260200280838360005b83811015610081578181015183820152602001610069565b5050505090500191505060405180910390f35b61009c610160565b6040805160ff9092168252519081900360200190f35b6100d2600480360360208110156100c857600080fd5b503560ff16610165565b005b6100f4600480360360208110156100ea57600080fd5b503560ff1661027f565b604080516001600160801b039092168252519081900360200190f35b61011861029d565b61012061029d565b60005b600281101561015a576001816002811061013957fe5b600302016001015482826002811061014d57fe5b6020020152600101610123565b50905090565b600190565b33600090815260208190526040902054819060ff166001146101bb57604051600160e51b62461bcd0281526004018080602001828103825260388152602001806102bc6038913960400191505060405180910390fd5b60018160ff16600281106101cb57fe5b600302016002015460ff161561022b5760408051600160e51b62461bcd02815260206004820152601460248201527f43616e646964617465206e6f7420666f756e642e000000000000000000000000604482015290519081900360640190fd5b5060ff16600090815260076020908152604080832080546001600160801b03808216600101166fffffffffffffffffffffffffffffffff19909116179055338352908290529020805460ff19166002179055565b60ff166000908152600760205260409020546001600160801b031690565b6040518060400160405280600290602082028038833950919291505056fe506572736f6e206973206e6f74207265676973746572656420666f72207468697320766f7465206f7220616c726561647920766f7465642ea165627a7a72305820db13aecfbe09f98d001164ba9d472796a523f3280e64a7a130963c60b306d8a60029";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_GETRESULTSFORCANDIDATE = "getResultsForCandidate";

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
                Arrays.<Type>asList(new Uint8(candidateId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getResultsForCandidate(BigInteger candidateId) {
        final Function function = new Function(FUNC_GETRESULTSFORCANDIDATE,
                Arrays.<Type>asList(new Uint8(candidateId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }
}
