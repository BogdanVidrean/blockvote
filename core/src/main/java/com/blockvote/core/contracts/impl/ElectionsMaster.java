package com.blockvote.core.contracts.impl;

import com.blockvote.core.contracts.interfaces.IElectionMaster;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class ElectionsMaster extends Contract implements IElectionMaster {
    public static final String FUNC_GETBALANCE = "getBalance";
    public static final String FUNC_CANADDRESSVOTE = "canAddressVote";
    public static final String FUNC_CHANGEOWNERMASTERACCOUNT = "changeOwnerMasterAccount";
    public static final String FUNC_CANADDRESSDEPLOYCONTRACT = "canAddressDeployContract";
    public static final String FUNC_GETADDRESSOFSOCIALSECURITYNUMBER = "getAddressOfSocialSecurityNumber";
    public static final String FUNC_ADDVOTER = "addVoter";
    public static final String FUNC_CANSSNVOTE = "canSsnVote";
    public static final String FUNC_ADDELECTION = "addElection";
    public static final String FUNC_GETELECTIONNAMES = "getElectionNames";
    public static final String FUNC_ADDORGANIZER = "addOrganizer";
    public static final String FUNC_REMOVEVOTER = "removeVoter";
    public static final String FUNC_GETELECTIONADDRESSES = "getElectionAddresses";
    private static final String BINARY = "608060405260008054600160a060020a0319163317905534801561002257600080fd5b50336000908152600360205260409020805460ff19166001179055610f508061004c6000396000f3fe6080604052600436106100c4576000357c010000000000000000000000000000000000000000000000000000000090048063bd545ee311610081578063bd545ee314610327578063ced9155c146103da578063e220cde51461041d578063e4725f0214610482578063ef135b37146104b5578063ff64374814610568576100c4565b806312065fe0146100c657806314390fb5146100ed57806337600fe8146101345780633a4db5ea14610167578063558ecaca1461019a5780635607395b14610269575b005b3480156100d257600080fd5b506100db61057d565b60408051918252519081900360200190f35b3480156100f957600080fd5b506101206004803603602081101561011057600080fd5b5035600160a060020a03166105e9565b604080519115158252519081900360200190f35b34801561014057600080fd5b506100c46004803603602081101561015757600080fd5b5035600160a060020a031661060a565b34801561017357600080fd5b506101206004803603602081101561018a57600080fd5b5035600160a060020a03166106b4565b3480156101a657600080fd5b5061024d600480360360208110156101bd57600080fd5b8101906020810181356401000000008111156101d857600080fd5b8201836020820111156101ea57600080fd5b8035906020019184600183028401116401000000008311171561020c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106d5945050505050565b60408051600160a060020a039092168252519081900360200190f35b34801561027557600080fd5b506100c46004803603604081101561028c57600080fd5b8101906020810181356401000000008111156102a757600080fd5b8201836020820111156102b957600080fd5b803590602001918460018302840111640100000000831117156102db57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955050509035600160a060020a0316915061079f9050565b34801561033357600080fd5b506101206004803603602081101561034a57600080fd5b81019060208101813564010000000081111561036557600080fd5b82018360208201111561037757600080fd5b8035906020019184600183028401116401000000008311171561039957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a16945050505050565b3480156103e657600080fd5b506100c4600480360360608110156103fd57600080fd5b50600160a060020a03813581169160208101359160409091013516610a96565b34801561042957600080fd5b50610432610b84565b60408051602080825283518183015283519192839290830191858101910280838360005b8381101561046e578181015183820152602001610456565b505050509050019250505060405180910390f35b34801561048e57600080fd5b506100c4600480360360208110156104a557600080fd5b5035600160a060020a0316610bdc565b3480156104c157600080fd5b506100c4600480360360208110156104d857600080fd5b8101906020810181356401000000008111156104f357600080fd5b82018360208201111561050557600080fd5b8035906020019184600183028401116401000000008311171561052757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610c65945050505050565b34801561057457600080fd5b50610432610ea3565b600080543390600160a060020a031681146105e2576040805160e560020a62461bcd02815260206004820152601a60248201527f4d61737465722070726976696c65676573207265717569726564000000000000604482015290519081900360640190fd5b5050303190565b600160a060020a031660009081526005602052604090205460ff1660011490565b6000543390600160a060020a0316811461066e576040805160e560020a62461bcd02815260206004820152601a60248201527f4d61737465722070726976696c65676573207265717569726564000000000000604482015290519081900360640190fd5b5060008054600160a060020a0390921673ffffffffffffffffffffffffffffffffffffffff1990921682178155908152600360205260409020805460ff19166001179055565b600160a060020a031660009081526003602052604090205460ff1660011490565b3360008181526003602052604081205490919060ff16151561072f576040805160e560020a62461bcd02815260206004820152601e6024820152600080516020610f05833981519152604482015290519081900360640190fd5b6004836040518082805190602001908083835b602083106107615780518252601f199092019160209182019101610742565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031695945050505050565b3360008181526003602052604090205460ff1615156107f6576040805160e560020a62461bcd02815260206004820152601e6024820152600080516020610f05833981519152604482015290519081900360640190fd5b826000600160a060020a03166004826040518082805190602001908083835b602083106108345780518252601f199092019160209182019101610815565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a03169290921491506108c59050576040805160e560020a62461bcd02815260206004820152601f60248201527f546865206164647265737320697320616c7265616479206120766f7465722e00604482015290519081900360640190fd5b600160a060020a038316600090815260056020526040902054839060ff1615610938576040805160e560020a62461bcd02815260206004820152601c60248201527f5468652061646472657373206973206e6f7420617661696c61626c6500000000604482015290519081900360640190fd5b604051600160a060020a0385169060009060059082818181858883f1935050505015801561096a573d6000803e3d6000fd5b50836004866040518082805190602001908083835b6020831061099e5780518252601f19909201916020918201910161097f565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03968716179055979093166000908152600590975250909420805460ff191660011790555050505050565b600080600160a060020a03166004836040518082805190602001908083835b60208310610a545780518252601f199092019160209182019101610a35565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a03169290921415949350505050565b600160a060020a038116600090815260036020526040902054819060ff161515610af8576040805160e560020a62461bcd02815260206004820152601e6024820152600080516020610f05833981519152604482015290519081900360640190fd5b50506001805480820182557fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0394909416939093179092556002805492830181556000527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace90910155565b60606002805480602002602001604051908101604052809291908181526020018280548015610bd257602002820191906000526020600020905b815481526020019060010190808311610bbe575b5050505050905090565b6000543390600160a060020a03168114610c40576040805160e560020a62461bcd02815260206004820152601a60248201527f4d61737465722070726976696c65676573207265717569726564000000000000604482015290519081900360640190fd5b50600160a060020a03166000908152600360205260409020805460ff19166001179055565b3360008181526003602052604090205460ff161515610cbc576040805160e560020a62461bcd02815260206004820152601e6024820152600080516020610f05833981519152604482015290519081900360640190fd5b816000600160a060020a03166004826040518082805190602001908083835b60208310610cfa5780518252601f199092019160209182019101610cdb565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031692909214159150610d8c9050576040805160e560020a62461bcd02815260206004820152601b60248201527f5468652061646472657373206973206e6f74206120766f7465722e0000000000604482015290519081900360640190fd5b60006004846040518082805190602001908083835b60208310610dc05780518252601f199092019160209182019101610da1565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031692505081159050610e9d576004846040518082805190602001908083835b60208310610e325780518252601f199092019160209182019101610e13565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420805473ffffffffffffffffffffffffffffffffffffffff19169055600160a060020a0385166000908152600590915292909220805460ff1916905550505b50505050565b60606001805480602002602001604051908101604052809291908181526020018280548015610bd257602002820191906000526020600020905b8154600160a060020a03168152600190910190602001808311610edd57505050505090509056fe4f7267616e697a65722070726976696c6c656765732072657175697265640000a165627a7a723058204bd2f712a2fb8b62cc27ab24df0ba49a4c6fa66b1ceb62d2692908151f7f20780029";

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

    public RemoteCall<TransactionReceipt> addElection(String electionAddress, byte[] electionName, String organizerAddress) {
        final Function function = new Function(
                FUNC_ADDELECTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(electionAddress),
                        new org.web3j.abi.datatypes.generated.Bytes32(electionName),
                        new org.web3j.abi.datatypes.Address(organizerAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getElectionNames() {
        final Function function = new Function(FUNC_GETELECTIONNAMES,
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
}
