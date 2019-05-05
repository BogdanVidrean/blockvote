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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161090f38038061090f833981018060405260a081101561003357600080fd5b815160208301516040840180519294919382019264010000000081111561005957600080fd5b8201602081018481111561006c57600080fd5b815185602082028301116401000000008211171561008957600080fd5b505060208281015160409384015160008054600160a060020a031916600160a060020a038b81169190911780835587517f3a4db5ea0000000000000000000000000000000000000000000000000000000081523360048201529751969950939750919590949290911692633a4db5ea92602480840193829003018186803b15801561011357600080fd5b505afa158015610127573d6000803e3d6000fd5b505050506040513d602081101561013d57600080fd5b5051905060018115151461019c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260348152602001806108db6034913960400191505060405180910390fd5b60008054604080517fced9155c000000000000000000000000000000000000000000000000000000008152306004820152602481018990523360448201529051600160a060020a039092169263ced9155c9260648084019382900301818387803b15801561020957600080fd5b505af115801561021d573d6000803e3d6000fd5b50505060048690555060005b84518160ff1610156102c35760016060604051908101604052808360ff168152602001878460ff1681518110151561025d57fe5b602090810290910181015182526001918101829052835480830185556000948552938190208351600390950201805460ff90951660ff19958616178155908301518183015560409092015160029092018054921515929093169190911790915501610229565b50506005919091556006555050506105fb806102e06000396000f3fe608060405234801561001057600080fd5b5060043610610068577c0100000000000000000000000000000000000000000000000000000000600035046314390fb5811461006d578063b3f98adc146100b4578063cc2ee196146100d6578063e3f8dfc21461012e575b600080fd5b6100a06004803603602081101561008357600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610173565b604080519115158252519081900360200190f35b6100d4600480360360208110156100ca57600080fd5b503560ff16610219565b005b6100de6104ef565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561011a578181015183820152602001610102565b505050509050019250505060405180910390f35b61014e6004803603602081101561014457600080fd5b503560ff16610577565b604080516fffffffffffffffffffffffffffffffff9092168252519081900360200190f35b60008054604080517f14390fb500000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff8581166004830152915191909216916314390fb5916024808301926020929190829003018186803b1580156101e757600080fd5b505afa1580156101fb573d6000803e3d6000fd5b505050506040513d602081101561021157600080fd5b505192915050565b600054604080517f14390fb50000000000000000000000000000000000000000000000000000000081523360048201529051839273ffffffffffffffffffffffffffffffffffffffff16916314390fb5916024808301926020929190829003018186803b15801561028957600080fd5b505afa15801561029d573d6000803e3d6000fd5b505050506040513d60208110156102b357600080fd5b505115156102f55760405160e560020a62461bcd02815260040180806020018281038252603181526020018061059f6031913960400191505060405180910390fd5b3360009081526003602052604090205460ff161561035d576040805160e560020a62461bcd02815260206004820152601f60248201527f5468697320616464726573732068617320616c726561647920766f7465642e00604482015290519081900360640190fd5b6001805460ff831690811061036e57fe5b600091825260209091206002600390920201015460ff1615156103db576040805160e560020a62461bcd02815260206004820152601160248201527f4f7074696f6e206e6f7420666f756e642e000000000000000000000000000000604482015290519081900360640190fd5b6005544211610434576040805160e560020a62461bcd02815260206004820152601e60248201527f54686520656c656374696f6e206469646e2774207374617274207965742e0000604482015290519081900360640190fd5b600654421061048d576040805160e560020a62461bcd02815260206004820152601960248201527f54686520656c656374696f6e2069732066696e69736865642e00000000000000604482015290519081900360640190fd5b5060ff16600090815260026020908152604080832080546fffffffffffffffffffffffffffffffff19811660016fffffffffffffffffffffffffffffffff9283168101909216179091553384526003909252909120805460ff19169091179055565b606080600180549050604051908082528060200260200182016040528015610521578160200160208202803883390190505b50905060005b60015481101561057157600180548290811061053f57fe5b906000526020600020906003020160010154828281518110151561055f57fe5b60209081029091010152600101610527565b50905090565b60ff166000908152600260205260409020546fffffffffffffffffffffffffffffffff169056fe41646472657373206973206e6f74207265676973746572656420666f7220766f74696e67207065726d697373696f6e732ea165627a7a72305820546a9745dd6a246fba864eb19b34023cafa25abc364606bfb80b23158187271800294f7267616e697a6572207065726d697373696f6e7320726571756972656420746f206465706c6f79206120636f6e74726163742e";

    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_GETOPTIONS = "getOptions";

    public static final String FUNC_GETRESULTSFOROPTION = "getResultsForOption";

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

    public static RemoteCall<Election> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String masterContractAddress, Bytes32 nameOfElection, List<Bytes32> initialOptions, BigInteger electionStartTime, BigInteger electionEndTime) {
        DynamicArray<Bytes32> bytes32DynamicArray = new DynamicArray<>(initialOptions);
        Field type = null;
        try {
            type = bytes32DynamicArray.getClass().getSuperclass().getDeclaredField("type");
            type.setAccessible(true);
            type.set(bytes32DynamicArray, org.web3j.abi.datatypes.generated.Bytes32.class.getCanonicalName());
            String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(masterContractAddress),
                    nameOfElection,
                    bytes32DynamicArray,
                    new org.web3j.abi.datatypes.generated.Uint256(electionStartTime),
                    new org.web3j.abi.datatypes.generated.Uint256(electionEndTime)));
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
