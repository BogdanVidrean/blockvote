package com.blockvote.core.contracts.impl;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.Field;
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
    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final String FUNC_VOTE = "vote";
    public static final String FUNC_GETOPTIONS = "getOptions";
    public static final String FUNC_GETRESULTSFOROPTION = "getResultsForOption";
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610beb380380610beb8339810180604052606081101561003357600080fd5b810190808051906020019092919080519060200190929190805164010000000081111561005f57600080fd5b8281019050602081018481111561007557600080fd5b815185602082028301116401000000008211171561009257600080fd5b5050929190505050826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16633a4db5ea336040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b15801561019657600080fd5b505afa1580156101aa573d6000803e3d6000fd5b505050506040513d60208110156101c057600080fd5b8101908080519060200190929190505050905060011515811515141515610232576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526034815260200180610bb76034913960400191505060405180910390fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663ced9155c3085336040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019350505050600060405180830381600087803b15801561032a57600080fd5b505af115801561033e573d6000803e3d6000fd5b505050508260048190555060008090505b82518160ff16101561041f5760016060604051908101604052808360ff168152602001858460ff1681518110151561038357fe5b906020019060200201518152602001600115158152509080600181540180825580915050906001820390600052602060002090600302016000909192909190915060008201518160000160006101000a81548160ff021916908360ff1602179055506020820151816001015560408201518160020160006101000a81548160ff021916908315150217905550505050808060010191505061034f565b5050505050610784806104336000396000f3fe608060405234801561001057600080fd5b5060043610610069576000357c01000000000000000000000000000000000000000000000000000000009004806314390fb51461006e578063b3f98adc146100ca578063cc2ee196146100fb578063e3f8dfc21461015a575b600080fd5b6100b06004803603602081101561008457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506101c3565b604051808215151515815260200191505060405180910390f35b6100f9600480360360208110156100e057600080fd5b81019080803560ff1690602001909291905050506102c1565b005b610103610649565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561014657808201518184015260208101905061012b565b505050509050019250505060405180910390f35b6101896004803603602081101561017057600080fd5b81019080803560ff1690602001909291905050506106e8565b60405180826fffffffffffffffffffffffffffffffff166fffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166314390fb5836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b15801561027f57600080fd5b505afa158015610293573d6000803e3d6000fd5b505050506040513d60208110156102a957600080fd5b81019080805190602001909291905050509050919050565b806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166314390fb5336040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060206040518083038186803b15801561037c57600080fd5b505afa158015610390573d6000803e3d6000fd5b505050506040513d60208110156103a657600080fd5b8101908080519060200190929190505050151561040e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260318152602001806107286031913960400191505060405180910390fd5b6000600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff161415156104d5576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f5468697320616464726573732068617320616c726561647920766f7465642e0081525060200191505060405180910390fd5b60018160ff168154811015156104e757fe5b906000526020600020906003020160020160009054906101000a900460ff16151561057a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260118152602001807f4f7074696f6e206e6f7420666f756e642e00000000000000000000000000000081525060200191505060405180910390fd5b6001600260008460ff1660ff16815260200190815260200160002060008282829054906101000a90046fffffffffffffffffffffffffffffffff160192506101000a8154816fffffffffffffffffffffffffffffffff02191690836fffffffffffffffffffffffffffffffff1602179055506001600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff1602179055505050565b6060806001805490506040519080825280602002602001820160405280156106805781602001602082028038833980820191505090505b50905060008090505b6001805490508110156106e0576001818154811015156106a557fe5b90600052602060002090600302016001015482828151811015156106c557fe5b90602001906020020181815250508080600101915050610689565b508091505090565b6000600260008360ff1660ff16815260200190815260200160002060009054906101000a90046fffffffffffffffffffffffffffffffff16905091905056fe41646472657373206973206e6f74207265676973746572656420666f7220766f74696e67207065726d697373696f6e732ea165627a7a7230582039db2d74cde09ab534daafbbb38aed0b6a58bfe559db0f31a6d6f572506c1bc200294f7267616e697a6572207065726d697373696f6e7320726571756972656420746f206465706c6f79206120636f6e74726163742e";

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

    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String masterContractAddress, Bytes32 nameOfElection, List<Bytes32> initialOptions) {
        DynamicArray<Bytes32> bytes32DynamicArray = new DynamicArray<>(initialOptions);
        Field type = null;
        try {
            type = bytes32DynamicArray.getClass().getSuperclass().getDeclaredField("type");
            type.setAccessible(true);
            type.set(bytes32DynamicArray, org.web3j.abi.datatypes.generated.Bytes32.class.getCanonicalName());
            String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(masterContractAddress),
                    nameOfElection,
                    bytes32DynamicArray));
            return deployRemoteCall(Election.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        final Function function = new Function(FUNC_CANADDRESSVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(votersAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger optionId) {
        final Function function = new Function(
                FUNC_VOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(optionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getOptions() {
        final Function function = new Function(FUNC_GETOPTIONS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {
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

    public RemoteCall<BigInteger> getResultsForOption(BigInteger optionId) {
        final Function function = new Function(FUNC_GETRESULTSFOROPTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(optionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }
}
