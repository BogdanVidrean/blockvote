package com.blockvote.core.contracts.impl;

import com.blockvote.core.contracts.interfaces.IElection;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class Election extends Contract implements IElection {
    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final String FUNC_GETENDTIME = "getEndTime";
    public static final String FUNC_GETSTARTTIME = "getStartTime";

    public static final String FUNC_GETRESULTS = "getResults";

    public static final String FUNC_ENDELECTION = "endElection";

    public static final String FUNC_VOTE = "vote";
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610be0380380610be0833981018060405260a081101561003357600080fd5b815160208301516040840180519294919382019264010000000081111561005957600080fd5b8201602081018481111561006c57600080fd5b815185602082028301116401000000008211171561008957600080fd5b505060208281015160409384015160008054600160a060020a031916600160a060020a038b81169190911780835587517f3a4db5ea0000000000000000000000000000000000000000000000000000000081523360048201529751969950939750919590949290911692633a4db5ea92602480840193829003018186803b15801561011357600080fd5b505afa158015610127573d6000803e3d6000fd5b505050506040513d602081101561013d57600080fd5b5051905060018115151461019c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526034815260200180610bac6034913960400191505060405180910390fd5b60008054604080517fced9155c000000000000000000000000000000000000000000000000000000008152306004820152602481018990523360448201529051600160a060020a039092169263ced9155c9260648084019382900301818387803b15801561020957600080fd5b505af115801561021d573d6000803e3d6000fd5b50505060048690555060005b84518160ff1610156103115760016060604051908101604052808360ff168152602001878460ff1681518110151561025d57fe5b6020908102909101810151825260019181018290528354808301855560009485528185208451600390920201805460ff90921660ff19928316178155918401518284015560409093015160029182018054911515919094161790925581548082018355928290529082047f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001608060020a036010948416949094026101000a939093021990921690915501610229565b505060059190915560065550506007805460ff1916905550610874806103386000396000f3fe608060405234801561001057600080fd5b506004361061009a576000357c01000000000000000000000000000000000000000000000000000000009004806359f784681161007857806359f7846814610158578063b3f98adc14610162578063c828371e14610182578063cc2ee1961461018a5761009a565b806314390fb51461009f578063439f5ac2146100e65780634717f97c14610100575b600080fd5b6100d2600480360360208110156100b557600080fd5b503573ffffffffffffffffffffffffffffffffffffffff16610192565b604080519115158252519081900360200190f35b6100ee610238565b60408051918252519081900360200190f35b61010861023e565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561014457818101518382015260200161012c565b505050509050019250505060405180910390f35b61016061033a565b005b6101606004803603602081101561017857600080fd5b503560ff1661047f565b6100ee610767565b61010861076d565b60008054604080517f14390fb500000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff8581166004830152915191909216916314390fb5916024808301926020929190829003018186803b15801561020657600080fd5b505afa15801561021a573d6000803e3d6000fd5b505050506040513d602081101561023057600080fd5b505192915050565b60065490565b60075460609060ff1615156001146102a0576040805160e560020a62461bcd02815260206004820152601d60248201527f54686520656c656374696f6e206973206e6f74206f766572207965742e000000604482015290519081900360640190fd5b600280548060200260200160405190810160405280929190818152602001828054801561033057602002820191906000526020600020906000905b82829054906101000a90046fffffffffffffffffffffffffffffffff166fffffffffffffffffffffffffffffffff1681526020019060100190602082600f010492830192600103820291508084116102db5790505b5050505050905090565b6006544211610393576040805160e560020a62461bcd02815260206004820152601960248201527f5468652074696d65206973206e6f74206f766572207965742e00000000000000604482015290519081900360640190fd5b600054604080517f3a4db5ea000000000000000000000000000000000000000000000000000000008152336004820152905173ffffffffffffffffffffffffffffffffffffffff90921691633a4db5ea91602480820192602092909190829003018186803b15801561040457600080fd5b505afa158015610418573d6000803e3d6000fd5b505050506040513d602081101561042e57600080fd5b505115156104705760405160e560020a62461bcd0281526004018080602001828103825260228152602001806108276022913960400191505060405180910390fd5b6007805460ff19166001179055565b600054604080517f14390fb50000000000000000000000000000000000000000000000000000000081523360048201529051839273ffffffffffffffffffffffffffffffffffffffff16916314390fb5916024808301926020929190829003018186803b1580156104ef57600080fd5b505afa158015610503573d6000803e3d6000fd5b505050506040513d602081101561051957600080fd5b5051151561055b5760405160e560020a62461bcd0281526004018080602001828103825260318152602001806107f66031913960400191505060405180910390fd5b3360009081526003602052604090205460ff16156105c3576040805160e560020a62461bcd02815260206004820152601f60248201527f5468697320616464726573732068617320616c726561647920766f7465642e00604482015290519081900360640190fd5b6001805460ff83169081106105d457fe5b600091825260209091206002600390920201015460ff161515610641576040805160e560020a62461bcd02815260206004820152601160248201527f4f7074696f6e206e6f7420666f756e642e000000000000000000000000000000604482015290519081900360640190fd5b600554421161069a576040805160e560020a62461bcd02815260206004820152601e60248201527f54686520656c656374696f6e206469646e2774207374617274207965742e0000604482015290519081900360640190fd5b60065442106106f3576040805160e560020a62461bcd02815260206004820152601960248201527f54686520656c656374696f6e2069732066696e69736865642e00000000000000604482015290519081900360640190fd5b600160028360ff1681548110151561070757fe5b60009182526020808320600283040180546fffffffffffffffffffffffffffffffff60106001958616026101000a8083048216909701811687029602191694909417909355338252600390925260409020805460ff191690911790555050565b60055490565b60608060018054905060405190808252806020026020018201604052801561079f578160200160208202803883390190505b50905060005b6001548110156107ef5760018054829081106107bd57fe5b90600052602060002090600302016001015482828151811015156107dd57fe5b602090810290910101526001016107a5565b5090509056fe41646472657373206973206e6f74207265676973746572656420666f7220766f74696e67207065726d697373696f6e732e4f6e6c79206f7267616e697a6572732063616e20656e6420656c656374696f6e732ea165627a7a72305820c933e3ddcea6ba22d7fb03d1df8413a417fa5431076ed22cd685772436e41eac00294f7267616e697a6572207065726d697373696f6e7320726571756972656420746f206465706c6f79206120636f6e74726163742e";

    public static final String FUNC_GETOPTIONS = "getOptions";

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

    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        final Function function = new Function(FUNC_CANADDRESSVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(votersAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> getEndTime() {
        final Function function = new Function(FUNC_GETENDTIME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List> getResults() {
        final Function function = new Function(FUNC_GETRESULTS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint128>>() {
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

    public RemoteCall<TransactionReceipt> endElection() {
        final Function function = new Function(
                FUNC_ENDELECTION,
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger optionId) {
        final Function function = new Function(
                FUNC_VOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(optionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getStartTime() {
        final Function function = new Function(FUNC_GETSTARTTIME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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
}
