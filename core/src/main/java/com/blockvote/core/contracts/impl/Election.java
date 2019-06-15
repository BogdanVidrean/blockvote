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
 * <p>Generated with web3j version 4.3.0.
 */
public class Election extends Contract implements IElection {
    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final String FUNC_GETENDTIME = "getEndTime";
    public static final String FUNC_GETRESULTS = "getResults";
    public static final String FUNC_ENDELECTION = "endElection";
    public static final String FUNC_VOTE = "vote";
    public static final String FUNC_GETSTARTTIME = "getStartTime";
    public static final String FUNC_GETOPTIONS = "getOptions";
    private static final String BINARY = "608060405234801561001057600080fd5b50604051610b00380380610b00833981810160405260a081101561003357600080fd5b815160208301516040840180519294919382019264010000000081111561005957600080fd5b8201602081018481111561006c57600080fd5b815185602082028301116401000000008211171561008957600080fd5b5050602082810151604093840151600080546001600160a01b0319166001600160a01b038b81169190911780835587517f3a4db5ea0000000000000000000000000000000000000000000000000000000081523360048201529751969950939750919590949290911692633a4db5ea92602480840193829003018186803b15801561011357600080fd5b505afa158015610127573d6000803e3d6000fd5b505050506040513d602081101561013d57600080fd5b5051905060018115151461019c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526034815260200180610acc6034913960400191505060405180910390fd5b60008054604080517fced9155c0000000000000000000000000000000000000000000000000000000081523060048201526024810189905233604482015290516001600160a01b039092169263ced9155c9260648084019382900301818387803b15801561020957600080fd5b505af115801561021d573d6000803e3d6000fd5b50505060048690555060005b84518160ff16101561030e57600160405180606001604052808360ff168152602001878460ff168151811061025a57fe5b602090810291909101810151825260019181018290528354808301855560009485528185208451600390920201805460ff1990811660ff9093169290921781559184015182840155604090930151600291820180549094169015151790925581548082018355928290527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace9183049190910180549282166010026101000a6001600160801b03021990921690915501610229565b505060059190915560065550506007805460ff1916905550610797806103356000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806359f784681161005b57806359f784681461012e578063b3f98adc14610138578063c828371e14610158578063cc2ee196146101605761007d565b806314390fb514610082578063439f5ac2146100bc5780634717f97c146100d6575b600080fd5b6100a86004803603602081101561009857600080fd5b50356001600160a01b0316610168565b604080519115158252519081900360200190f35b6100c46101e8565b60408051918252519081900360200190f35b6100de6101ee565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561011a578181015183820152602001610102565b505050509050019250505060405180910390f35b6101366102d5565b005b6101366004803603602081101561014e57600080fd5b503560ff166103ec565b6100c4610683565b6100de610689565b60008054604080516314390fb560e01b81526001600160a01b038581166004830152915191909216916314390fb5916024808301926020929190829003018186803b1580156101b657600080fd5b505afa1580156101ca573d6000803e3d6000fd5b505050506040513d60208110156101e057600080fd5b505192915050565b60065490565b60075460609060ff16151560011461024d576040805162461bcd60e51b815260206004820152601d60248201527f54686520656c656374696f6e206973206e6f74206f766572207965742e000000604482015290519081900360640190fd5b60028054806020026020016040519081016040528092919081815260200182805480156102cb57602002820191906000526020600020906000905b82829054906101000a90046001600160801b03166001600160801b031681526020019060100190602082600f010492830192600103820291508084116102885790505b5050505050905090565b600654421161032b576040805162461bcd60e51b815260206004820152601960248201527f5468652074696d65206973206e6f74206f766572207965742e00000000000000604482015290519081900360640190fd5b60005460408051631d26daf560e11b815233600482015290516001600160a01b0390921691633a4db5ea91602480820192602092909190829003018186803b15801561037657600080fd5b505afa15801561038a573d6000803e3d6000fd5b505050506040513d60208110156103a057600080fd5b50516103dd5760405162461bcd60e51b81526004018080602001828103825260228152602001806107416022913960400191505060405180910390fd5b6007805460ff19166001179055565b600054604080516314390fb560e01b8152336004820152905183926001600160a01b0316916314390fb5916024808301926020929190829003018186803b15801561043657600080fd5b505afa15801561044a573d6000803e3d6000fd5b505050506040513d602081101561046057600080fd5b505161049d5760405162461bcd60e51b81526004018080602001828103825260318152602001806107106031913960400191505060405180910390fd5b3360009081526003602052604090205460ff1615610502576040805162461bcd60e51b815260206004820152601f60248201527f5468697320616464726573732068617320616c726561647920766f7465642e00604482015290519081900360640190fd5b60018160ff168154811061051257fe5b600091825260209091206002600390920201015460ff1661056e576040805162461bcd60e51b815260206004820152601160248201527027b83a34b7b7103737ba103337bab7321760791b604482015290519081900360640190fd5b60055442116105c4576040805162461bcd60e51b815260206004820152601e60248201527f54686520656c656374696f6e206469646e2774207374617274207965742e0000604482015290519081900360640190fd5b600654421061061a576040805162461bcd60e51b815260206004820152601960248201527f54686520656c656374696f6e2069732066696e69736865642e00000000000000604482015290519081900360640190fd5b600160028360ff168154811061062c57fe5b60009182526020808320600283040180546001600160801b0360106001958616026101000a8083048216909701811687029602191694909417909355338252600390925260409020805460ff191690911790555050565b60055490565b6060806001805490506040519080825280602002602001820160405280156106bb578160200160208202803883390190505b50905060005b60015481101561070957600181815481106106d857fe5b9060005260206000209060030201600101548282815181106106f657fe5b60209081029190910101526001016106c1565b5090509056fe41646472657373206973206e6f74207265676973746572656420666f7220766f74696e67207065726d697373696f6e732e4f6e6c79206f7267616e697a6572732063616e20656e6420656c656374696f6e732ea265627a7a723058203e78ae22791134a6a36dfe28af30f8f863f8f392ab539d1589da22512258a8c164736f6c634300050900324f7267616e697a6572207065726d697373696f6e7320726571756972656420746f206465706c6f79206120636f6e74726163742e";

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

}
