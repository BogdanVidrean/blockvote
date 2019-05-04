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
    private static final String BINARY = "6080604052336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005057600080fd5b506001600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff1602179055506116f5806100b96000396000f3fe6080604052600436106100c4576000357c010000000000000000000000000000000000000000000000000000000090048063bd545ee311610081578063bd545ee314610404578063ced9155c146104e4578063e220cde51461055f578063e4725f02146105cb578063ef135b371461061c578063ff643748146106e4576100c4565b806312065fe0146100c657806314390fb5146100f157806337600fe81461015a5780633a4db5ea146101ab578063558ecaca146102145780635607395b1461031c575b005b3480156100d257600080fd5b506100db610750565b6040518082815260200191505060405180910390f35b3480156100fd57600080fd5b506101406004803603602081101561011457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610835565b604051808215151515815260200191505060405180910390f35b34801561016657600080fd5b506101a96004803603602081101561017d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610891565b005b3480156101b757600080fd5b506101fa600480360360208110156101ce57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506109f3565b604051808215151515815260200191505060405180910390f35b34801561022057600080fd5b506102da6004803603602081101561023757600080fd5b810190808035906020019064010000000081111561025457600080fd5b82018360208201111561026657600080fd5b8035906020019184600183028401116401000000008311171561028857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610a4f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561032857600080fd5b506104026004803603604081101561033f57600080fd5b810190808035906020019064010000000081111561035c57600080fd5b82018360208201111561036e57600080fd5b8035906020019184600183028401116401000000008311171561039057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610bae565b005b34801561041057600080fd5b506104ca6004803603602081101561042757600080fd5b810190808035906020019064010000000081111561044457600080fd5b82018360208201111561045657600080fd5b8035906020019184600183028401116401000000008311171561047857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610ef8565b604051808215151515815260200191505060405180910390f35b3480156104f057600080fd5b5061055d6004803603606081101561050757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610fbc565b005b34801561056b57600080fd5b5061057461111d565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156105b757808201518184015260208101905061059c565b505050509050019250505060405180910390f35b3480156105d757600080fd5b5061061a600480360360208110156105ee57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611175565b005b34801561062857600080fd5b506106e26004803603602081101561063f57600080fd5b810190808035906020019064010000000081111561065c57600080fd5b82018360208201111561066e57600080fd5b8035906020019184600183028401116401000000008311171561069057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050611297565b005b3480156106f057600080fd5b506106f961163b565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561073c578082015181840152602081019050610721565b505050509050019250505060405180910390f35b6000336000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515610817576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4d61737465722070726976696c6567657320726571756972656400000000000081525060200191505060405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff163191505090565b60006001600560008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff16149050919050565b336000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515610956576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4d61737465722070726976696c6567657320726571756972656400000000000081525060200191505060405180910390fd5b816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff1602179055505050565b60006001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff16149050919050565b6000336000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff1614151515610b1a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f4f7267616e697a65722070726976696c6c65676573207265717569726564000081525060200191505060405180910390fd5b6004836040518082805190602001908083835b602083101515610b525780518252602082019150602081019050602083039250610b2d565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b336000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff1614151515610c77576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f4f7267616e697a65722070726976696c6c65676573207265717569726564000081525060200191505060405180910390fd5b82600073ffffffffffffffffffffffffffffffffffffffff166004826040518082805190602001908083835b602083101515610cc85780518252602082019150602081019050602083039250610ca3565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610da7576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f546865206164647265737320697320616c7265616479206120766f7465722e0081525060200191505060405180910390fd5b8273ffffffffffffffffffffffffffffffffffffffff166108fc60059081150290604051600060405180830381858888f19350505050158015610dee573d6000803e3d6000fd5b50826004856040518082805190602001908083835b602083101515610e285780518252602082019150602081019050602083039250610e03565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600560008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff16021790555050505050565b60008073ffffffffffffffffffffffffffffffffffffffff166004836040518082805190602001908083835b602083101515610f495780518252602082019150602081019050602083039250610f24565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614159050919050565b806000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff1614151515611085576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f4f7267616e697a65722070726976696c6c65676573207265717569726564000081525060200191505060405180910390fd5b60018490806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050600283908060018154018082558091505090600182039060005260206000200160009091929091909150555050505050565b6060600280548060200260200160405190810160405280929190818152602001828054801561116b57602002820191906000526020600020905b815481526020019060010190808311611157575b5050505050905090565b336000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151561123a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4d61737465722070726976696c6567657320726571756972656400000000000081525060200191505060405180910390fd5b6001600360008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff021916908360ff1602179055505050565b336000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1660ff1614151515611360576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f4f7267616e697a65722070726976696c6c65676573207265717569726564000081525060200191505060405180910390fd5b81600073ffffffffffffffffffffffffffffffffffffffff166004826040518082805190602001908083835b6020831015156113b1578051825260208201915060208101905060208303925061138c565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151515611491576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f5468652061646472657373206973206e6f74206120766f7465722e000000000081525060200191505060405180910390fd5b60006004846040518082805190602001908083835b6020831015156114cb57805182526020820191506020810190506020830392506114a6565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141515611635576004846040518082805190602001908083835b60208310151561158f578051825260208201915060208101905060208303925061156a565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055600560008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81549060ff02191690555b50505050565b606060018054806020026020016040519081016040528092919081815260200182805480156116bf57602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311611675575b505050505090509056fea165627a7a72305820f8ad3f00b25001fddb21863a5325e896faf20c6ebcd2ad6a8a327ce33fa0c7400029";

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
