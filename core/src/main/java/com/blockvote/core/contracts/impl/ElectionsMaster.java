package com.blockvote.core.contracts.impl;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
    public static final String FUNC_ADDELECTION = "addElection";
    public static final String FUNC_ADDORGANIZER = "addOrganizer";
    public static final String FUNC_ADDVOTER = "addVoter";
    public static final String FUNC_CANADDRESSDEPLOYCONTRACT = "canAddressDeployContract";
    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final String FUNC_CANSSNVOTE = "canSsnVote";
    public static final String FUNC_CHANGEOWNERMASTERACCOUNT = "changeOwnerMasterAccount";
    public static final String FUNC_GETADDRESSOFSOCIALSECURITYNUMBER = "getAddressOfSocialSecurityNumber";
    public static final String FUNC_GETBALANCE = "getBalance";
    public static final String FUNC_GETELECTIONADDRESSES = "getElectionAddresses";
    public static final String FUNC_GETELECTIONNAMES = "getElectionNames";
    public static final String FUNC_REMOVEVOTER = "removeVoter";
    public static final Event ELECTIONCREATED_EVENT = new Event("ElectionCreated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Bytes32>(true) {
            }));
    private static final String BINARY = "6080604052600080546001600160a01b0319163317905534801561002257600080fd5b50336000908152600360205260409020805460ff19166001179055610f298061004c6000396000f3fe6080604052600436106100ab5760003560e01c8063bd545ee311610064578063bd545ee314610316578063ced9155c146103c7578063e220cde51461040a578063e4725f021461046f578063ef135b37146104a2578063ff64374814610553576100b2565b806312065fe0146100b757806314390fb5146100de57806337600fe8146101255780633a4db5ea1461015a578063558ecaca1461018d5780635607395b1461025a576100b2565b366100b257005b600080fd5b3480156100c357600080fd5b506100cc610568565b60408051918252519081900360200190f35b3480156100ea57600080fd5b506101116004803603602081101561010157600080fd5b50356001600160a01b03166105ce565b604080519115158252519081900360200190f35b34801561013157600080fd5b506101586004803603602081101561014857600080fd5b50356001600160a01b03166105ef565b005b34801561016657600080fd5b506101116004803603602081101561017d57600080fd5b50356001600160a01b031661069b565b34801561019957600080fd5b5061023e600480360360208110156101b057600080fd5b810190602081018135600160201b8111156101ca57600080fd5b8201836020820111156101dc57600080fd5b803590602001918460018302840111600160201b831117156101fd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106bc945050505050565b604080516001600160a01b039092168252519081900360200190f35b34801561026657600080fd5b506101586004803603604081101561027d57600080fd5b810190602081018135600160201b81111561029757600080fd5b8201836020820111156102a957600080fd5b803590602001918460018302840111600160201b831117156102ca57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506107819050565b34801561032257600080fd5b506101116004803603602081101561033957600080fd5b810190602081018135600160201b81111561035357600080fd5b82018360208201111561036557600080fd5b803590602001918460018302840111600160201b8311171561038657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506109e7945050505050565b3480156103d357600080fd5b50610158600480360360608110156103ea57600080fd5b506001600160a01b03813581169160208101359160409091013516610a67565b34801561041657600080fd5b5061041f610b6e565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561045b578181015183820152602001610443565b505050509050019250505060405180910390f35b34801561047b57600080fd5b506101586004803603602081101561049257600080fd5b50356001600160a01b0316610bc6565b3480156104ae57600080fd5b50610158600480360360208110156104c557600080fd5b810190602081018135600160201b8111156104df57600080fd5b8201836020820111156104f157600080fd5b803590602001918460018302840111600160201b8311171561051257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610c49945050505050565b34801561055f57600080fd5b5061041f610e72565b6000805433906001600160a01b031681146105c7576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b4791505090565b6001600160a01b031660009081526005602052604090205460ff1660011490565b60005433906001600160a01b0316811461064d576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b50600080546001600160a01b03908116825260036020526040808320805460ff1990811690915583546001600160a01b03191694909216938417835592825291902080549091166001179055565b6001600160a01b031660009081526003602052604090205460ff1660011490565b3360008181526003602052604081205490919060ff16610711576040805162461bcd60e51b815260206004820152601d6024820152600080516020610ed4833981519152604482015290519081900360640190fd5b6004836040518082805190602001908083835b602083106107435780518252601f199092019160209182019101610724565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031695945050505050565b3360008181526003602052604090205460ff166107d3576040805162461bcd60e51b815260206004820152601d6024820152600080516020610ed4833981519152604482015290519081900360640190fd5b8260006001600160a01b03166004826040518082805190602001908083835b602083106108115780518252601f1990920191602091820191016107f2565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031692909214915061089f9050576040805162461bcd60e51b815260206004820152601f60248201527f546865206164647265737320697320616c7265616479206120766f7465722e00604482015290519081900360640190fd5b6001600160a01b038316600090815260056020526040902054839060ff161561090f576040805162461bcd60e51b815260206004820152601c60248201527f5468652061646472657373206973206e6f7420617661696c61626c6500000000604482015290519081900360640190fd5b6040516001600160a01b03851690600090678ac7230489e800009082818181858883f19350505050158015610948573d6000803e3d6000fd5b50836004866040518082805190602001908083835b6020831061097c5780518252601f19909201916020918201910161095d565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055979093166000908152600590975250909420805460ff191660011790555050505050565b6000806001600160a01b03166004836040518082805190602001908083835b60208310610a255780518252601f199092019160209182019101610a06565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03169290921415949350505050565b6001600160a01b038116600090815260036020526040902054819060ff16610ac4576040805162461bcd60e51b815260206004820152601d6024820152600080516020610ed4833981519152604482015290519081900360640190fd5b6001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b03871690811790915560028054928301815560009081527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace90920185905560405185927f323543994de54d846d979fe510cd4d9e7042e801c01392af4abd1582484de63a91a350505050565b60606002805480602002602001604051908101604052809291908181526020018280548015610bbc57602002820191906000526020600020905b815481526020019060010190808311610ba8575b5050505050905090565b60005433906001600160a01b03168114610c24576040805162461bcd60e51b815260206004820152601a60248201527913585cdd195c881c1c9a5d9a5b1959d95cc81c995c5d5a5c995960321b604482015290519081900360640190fd5b506001600160a01b03166000908152600360205260409020805460ff19166001179055565b3360008181526003602052604090205460ff16610c9b576040805162461bcd60e51b815260206004820152601d6024820152600080516020610ed4833981519152604482015290519081900360640190fd5b8160006001600160a01b03166004826040518082805190602001908083835b60208310610cd95780518252601f199092019160209182019101610cba565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031692909214159150610d689050576040805162461bcd60e51b815260206004820152601b60248201527f5468652061646472657373206973206e6f74206120766f7465722e0000000000604482015290519081900360640190fd5b60006004846040518082805190602001908083835b60208310610d9c5780518252601f199092019160209182019101610d7d565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031692505081159050610e6c576004846040518082805190602001908083835b60208310610e0e5780518252601f199092019160209182019101610def565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b03191690556001600160a01b0385166000908152600590915292909220805460ff1916905550505b50505050565b60606001805480602002602001604051908101604052809291908181526020018280548015610bbc57602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610eac57505050505090509056fe4f7267616e697a65722070726976696c65676573207265717569726564000000a2646970667358221220c04028288abf9d8b93820012a681ba7e20e443333a7d0cdf2e7502b9143e8ce564736f6c63430006000033";
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, ElectionCreatedEventResponse>() {
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

    public RemoteCall<TransactionReceipt> addElection(String electionAddress, byte[] electionName, String organizerAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(electionAddress),
                        new org.web3j.abi.datatypes.generated.Bytes32(electionName),
                        new org.web3j.abi.datatypes.Address(organizerAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addOrganizer(String newOrganizer) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDORGANIZER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOrganizer)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addVoter(String socialSecurityNumber, String voterAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber),
                        new org.web3j.abi.datatypes.Address(voterAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> canAddressDeployContract(String organizerAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANADDRESSDEPLOYCONTRACT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(organizerAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> canAddressVote(String votersAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANADDRESSVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(votersAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<Boolean> canSsnVote(String voterSsn) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANSSNVOTE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(voterSsn)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> changeOwnerMasterAccount(String newOwnerMasterAccount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEOWNERMASTERACCOUNT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwnerMasterAccount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getAddressOfSocialSecurityNumber(String socialSecurityNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETADDRESSOFSOCIALSECURITYNUMBER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getBalance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETBALANCE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<List> getElectionAddresses() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETELECTIONADDRESSES,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, List.class);
    }

    public RemoteCall<List> getElectionNames() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETELECTIONNAMES,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallSingleValueReturn(function, List.class);
    }

    public RemoteCall<TransactionReceipt> removeVoter(String socialSecurityNumber) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEVOTER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socialSecurityNumber)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static class ElectionCreatedEventResponse {
        public Log log;

        public String electionAddress;

        public byte[] electionName;
    }
}
