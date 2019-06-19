package com.blockvote.core.contracts.impl;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import io.reactivex.Flowable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
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
public class ElectionsMaster extends Contract implements IElectionMaster {
    public static final String FUNC_GETELECTIONNAME = "getElectionName";
    public static final String FUNC_ADDELECTION = "addElection";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final Event ELECTIONCREATED_EVENT = new Event("ElectionCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Utf8String>(true) {
            }));

    public static final String FUNC_CHANGEOWNERMASTERACCOUNT = "changeOwnerMasterAccount";

    public static final String FUNC_CANADDRESSDEPLOYCONTRACT = "canAddressDeployContract";

    public static final String FUNC_GETADDRESSOFSOCIALSECURITYNUMBER = "getAddressOfSocialSecurityNumber";

    public static final String FUNC_ADDVOTER = "addVoter";

    public static final String FUNC_CANSSNVOTE = "canSsnVote";

    public static final String FUNC_ADDORGANIZER = "addOrganizer";

    public static final String FUNC_REMOVEVOTER = "removeVoter";

    public static final String FUNC_GETELECTIONADDRESSES = "getElectionAddresses";
    private static final String BINARY = "6080604052600080546001600160a01b0319163317905534801561002257600080fd5b50336000908152600360205260409020805460ff191660011790556111608061004c6000396000f3fe6080604052600436106100a65760003560e01c8063558ecaca11610064578063558ecaca146102f05780635607395b146103bd578063bd545ee314610479578063e4725f021461052a578063ef135b371461055d578063ff6437481461060e576100a6565b806262eca9146100a857806312065fe01461015057806314390fb5146101775780631956a32b146101be57806337600fe81461028a5780633a4db5ea146102bd575b005b3480156100b457600080fd5b506100db600480360360208110156100cb57600080fd5b50356001600160a01b0316610673565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101155781810151838201526020016100fd565b50505050905090810190601f1680156101425780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561015c57600080fd5b5061016561071c565b60408051918252519081900360200190f35b34801561018357600080fd5b506101aa6004803603602081101561019a57600080fd5b50356001600160a01b0316610784565b604080519115158252519081900360200190f35b3480156101ca57600080fd5b506100a6600480360360608110156101e157600080fd5b6001600160a01b038235169190810190604081016020820135600160201b81111561020b57600080fd5b82018360208201111561021d57600080fd5b803590602001918460018302840111600160201b8311171561023e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506107a59050565b34801561029657600080fd5b506100a6600480360360208110156102ad57600080fd5b50356001600160a01b03166108fd565b3480156102c957600080fd5b506101aa600480360360208110156102e057600080fd5b50356001600160a01b03166109a9565b3480156102fc57600080fd5b506103a16004803603602081101561031357600080fd5b810190602081018135600160201b81111561032d57600080fd5b82018360208201111561033f57600080fd5b803590602001918460018302840111600160201b8311171561036057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506109ca945050505050565b604080516001600160a01b039092168252519081900360200190f35b3480156103c957600080fd5b506100a6600480360360408110156103e057600080fd5b810190602081018135600160201b8111156103fa57600080fd5b82018360208201111561040c57600080fd5b803590602001918460018302840111600160201b8311171561042d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b03169150610a8f9050565b34801561048557600080fd5b506101aa6004803603602081101561049c57600080fd5b810190602081018135600160201b8111156104b657600080fd5b8201836020820111156104c857600080fd5b803590602001918460018302840111600160201b831117156104e957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610cee945050505050565b34801561053657600080fd5b506100a66004803603602081101561054d57600080fd5b50356001600160a01b0316610d6e565b34801561056957600080fd5b506100a66004803603602081101561058057600080fd5b810190602081018135600160201b81111561059a57600080fd5b8201836020820111156105ac57600080fd5b803590602001918460018302840111600160201b831117156105cd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610df1945050505050565b34801561061a57600080fd5b5061062361101a565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561065f578181015183820152602001610647565b505050509050019250505060405180910390f35b6001600160a01b038116600090815260026020818152604092839020805484516001821615610100026000190190911693909304601f810183900483028401830190945283835260609390918301828280156107105780601f106106e557610100808354040283529160200191610710565b820191906000526020600020905b8154815290600101906020018083116106f357829003601f168201915b50505050509050919050565b6000805433906001600160a01b0316811461077b576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b303191505b5090565b6001600160a01b031660009081526005602052604090205460ff1660011490565b6001600160a01b038116600090815260036020526040902054819060ff16610802576040805162461bcd60e51b815260206004820152601e602482015260008051602061110c833981519152604482015290519081900360640190fd5b600180548082019091557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038616908117909155600090815260026020908152604090912084516108699286019061107d565b50826040518082805190602001908083835b6020831061089a5780518252601f19909201916020918201910161087b565b5181516020939093036101000a6000190180199091169216919091179052604051920182900382209350506001600160a01b03871691507ffc9d99ce10cee1afccbe779a0f054471bb2853ee1a8072f00ba236f36bccd92090600090a350505050565b60005433906001600160a01b0316811461095b576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b50600080546001600160a01b03908116825260036020526040808320805460ff1990811690915583546001600160a01b03191694909216938417835592825291902080549091166001179055565b6001600160a01b031660009081526003602052604090205460ff1660011490565b3360008181526003602052604081205490919060ff16610a1f576040805162461bcd60e51b815260206004820152601e602482015260008051602061110c833981519152604482015290519081900360640190fd5b6004836040518082805190602001908083835b60208310610a515780518252601f199092019160209182019101610a32565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031695945050505050565b3360008181526003602052604090205460ff16610ae1576040805162461bcd60e51b815260206004820152601e602482015260008051602061110c833981519152604482015290519081900360640190fd5b8260006001600160a01b03166004826040518082805190602001908083835b60208310610b1f5780518252601f199092019160209182019101610b00565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b0316929092149150610bad9050576040805162461bcd60e51b815260206004820152601f60248201527f546865206164647265737320697320616c7265616479206120766f7465722e00604482015290519081900360640190fd5b6001600160a01b038316600090815260056020526040902054839060ff1615610c1d576040805162461bcd60e51b815260206004820152601c60248201527f5468652061646472657373206973206e6f7420617661696c61626c6500000000604482015290519081900360640190fd5b6040516001600160a01b0385169060009060059082818181858883f19350505050158015610c4f573d6000803e3d6000fd5b50836004866040518082805190602001908083835b60208310610c835780518252601f199092019160209182019101610c64565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055979093166000908152600590975250909420805460ff191660011790555050505050565b6000806001600160a01b03166004836040518082805190602001908083835b60208310610d2c5780518252601f199092019160209182019101610d0d565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03169290921415949350505050565b60005433906001600160a01b03168114610dcc576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b506001600160a01b03166000908152600360205260409020805460ff19166001179055565b3360008181526003602052604090205460ff16610e43576040805162461bcd60e51b815260206004820152601e602482015260008051602061110c833981519152604482015290519081900360640190fd5b8160006001600160a01b03166004826040518082805190602001908083835b60208310610e815780518252601f199092019160209182019101610e62565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031692909214159150610f109050576040805162461bcd60e51b815260206004820152601b60248201527f5468652061646472657373206973206e6f74206120766f7465722e0000000000604482015290519081900360640190fd5b60006004846040518082805190602001908083835b60208310610f445780518252601f199092019160209182019101610f25565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031692505081159050611014576004846040518082805190602001908083835b60208310610fb65780518252601f199092019160209182019101610f97565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b03191690556001600160a01b0385166000908152600590915292909220805460ff1916905550505b50505050565b6060600180548060200260200160405190810160405280929190818152602001828054801561107257602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311611054575b505050505090505b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110be57805160ff19168380011785556110eb565b828001600101855582156110eb579182015b828111156110eb5782518255916020019190600101906110d0565b506107809261107a9250905b8082111561078057600081556001016110f756fe4f7267616e697a65722070726976696c6c656765732072657175697265640000a265627a7a72305820a4cc313e35a20ac5f1796323561db451e2417ef8036a875137953ff36d752e5564736f6c63430005090032";
    ;

    @Deprecated
    protected ElectionsMaster(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ElectionsMaster(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ElectionsMaster(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ElectionsMaster(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ElectionsMaster load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ElectionsMaster(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ElectionsMaster load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ElectionsMaster(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ElectionsMaster load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ElectionsMaster(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ElectionsMaster load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ElectionsMaster(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ElectionsMaster> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ElectionsMaster.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<ElectionsMaster> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ElectionsMaster.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ElectionsMaster> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ElectionsMaster.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ElectionsMaster> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ElectionsMaster.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteCall<String> getElectionName(String eleciotnAddress) {
        final Function function = new Function(FUNC_GETELECTIONNAME,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(eleciotnAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        final Function function = new Function(FUNC_CANADDRESSVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(votersAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> addElection(String electionAddress, String electionName, String organizerAddress) {
        final Function function = new Function(
                FUNC_ADDELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(electionAddress),
                        new org.web3j.abi.datatypes.Utf8String(electionName),
                        new org.web3j.abi.datatypes.Address(organizerAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<ElectionCreatedEventResponse> getElectionCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ELECTIONCREATED_EVENT, transactionReceipt);
        ArrayList<ElectionCreatedEventResponse> responses = new ArrayList<ElectionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ElectionCreatedEventResponse typedResponse = new ElectionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.electionAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.electionName = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ElectionCreatedEventResponse>() {
            @Override
            public ElectionCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ELECTIONCREATED_EVENT, log);
                ElectionCreatedEventResponse typedResponse = new ElectionCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.electionAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.electionName = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ElectionCreatedEventResponse> electionCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ELECTIONCREATED_EVENT));
        return electionCreatedEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> changeOwnerMasterAccount(String newOwnerMasterAccount) {
        final Function function = new Function(
                FUNC_CHANGEOWNERMASTERACCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwnerMasterAccount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> canAddressDeployContract(String organizerAddress) {
        final Function function = new Function(FUNC_CANADDRESSDEPLOYCONTRACT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(organizerAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> getAddressOfSocialSecurityNumber(String socialSecurityNumber) {
        final Function function = new Function(FUNC_GETADDRESSOFSOCIALSECURITYNUMBER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addVoter(String socialSecurityNumber, String voterAddress) {
        final Function function = new Function(
                FUNC_ADDVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber),
                        new org.web3j.abi.datatypes.Address(voterAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> canSsnVote(String voterSsn) {
        final Function function = new Function(FUNC_CANSSNVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(voterSsn)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> addOrganizer(String newOrganizer) {
        final Function function = new Function(
                FUNC_ADDORGANIZER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOrganizer)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeVoter(String socialSecurityNumber) {
        final Function function = new Function(
                FUNC_REMOVEVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getElectionAddresses() {
        final Function function = new Function(FUNC_GETELECTIONADDRESSES,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
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

    public static class ElectionCreatedEventResponse {
        public Log log;

        public String electionAddress;

        public byte[] electionName;
    }
}
