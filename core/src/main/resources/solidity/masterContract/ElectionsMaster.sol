pragma solidity ^0.5.15;
contract ElectionsMaster {

    address private ownerMasterAddress = msg.sender;

    address[] private elections;
    bytes32[] private electionsNames;

    mapping(address => uint8) private organizersMapping;

    mapping(string => address) private votersSocialSecurityNumbers;

    mapping(address => uint8) private votersAddresses;

    uint private constant votersInitialBalance = 10000000000000000000;

    constructor() public {
        organizersMapping[msg.sender] = 1;
    }

    event ElectionCreated(
        address indexed electionAddress,
        bytes32 indexed electionName
    );

    // Modifiers

    modifier isMasterAccount(address senderAddress) {
        require(senderAddress == ownerMasterAddress, "Master privileges required");
        _;
    }

    modifier isOrganizer(address senderAddress) {
        require(organizersMapping[senderAddress] != 0, "Organizer privileges required");
        _;
    }

    // Master Account API

    function() external payable {
    }

    function changeOwnerMasterAccount(address newOwnerMasterAccount) public isMasterAccount(msg.sender) {
        organizersMapping[ownerMasterAddress] = 0;
        ownerMasterAddress = newOwnerMasterAccount;
        organizersMapping[newOwnerMasterAccount] = 1;
    }

    function addOrganizer(address newOrganizer) public isMasterAccount(msg.sender) {
        organizersMapping[newOrganizer] = 1;
    }

    function getBalance() public view isMasterAccount(msg.sender) returns(uint) {
        return address(this).balance;
    }

    // Organizer API

    modifier isNotVoterAlready(string memory socialSecurityNumber) {
        require(
            votersSocialSecurityNumbers[socialSecurityNumber] == address(0x0),
            "The address is already a voter."
        );
        _;
    }

    modifier isVoter(string memory socialSecurityNumber) {
        require(
            votersSocialSecurityNumbers[socialSecurityNumber] !=  address(0x0),
            "The address is not a voter."
        );
        _;
    }

    modifier addressIsNotInUse(address votersAddress) {
        require(
            votersAddresses[votersAddress] == 0,
            "The address is not available"
        );
        _;
    }

    function addElection(address electionAddress,
        bytes32 electionName,
        address organizerAddress) public isOrganizer(organizerAddress) {
        elections.push(electionAddress);
        electionsNames.push(electionName);
        emit ElectionCreated(electionAddress, electionName);
    }

    function addVoter(string memory socialSecurityNumber, address payable voterAddress) public isOrganizer(msg.sender) isNotVoterAlready(socialSecurityNumber) addressIsNotInUse(voterAddress) {
        voterAddress.transfer(votersInitialBalance);
        votersSocialSecurityNumbers[socialSecurityNumber] = voterAddress;
        votersAddresses[voterAddress] = 1;
    }

    function getAddressOfSocialSecurityNumber(string memory socialSecurityNumber) public view isOrganizer(msg.sender) returns(address) {
        return votersSocialSecurityNumbers[socialSecurityNumber];
    }

    function removeVoter(string memory socialSecurityNumber) public isOrganizer(msg.sender) isVoter(socialSecurityNumber) {
        address votersAddress = votersSocialSecurityNumbers[socialSecurityNumber];
        if (votersAddress != address(0x0)) {
            delete votersSocialSecurityNumbers[socialSecurityNumber];
            delete votersAddresses[votersAddress];
        }
    }

    function getElectionAddresses() public view returns(address[] memory) {
        return elections;
    }

    function getElectionNames() public view returns (bytes32[] memory) {
        return electionsNames;
    }

    function canAddressDeployContract(address organizerAddress) public view returns(bool) {
        return (organizersMapping[organizerAddress] == 1);
    }

    //  Voter API

    function canSsnVote(string memory voterSsn) public view returns(bool) {
        return (votersSocialSecurityNumbers[voterSsn] != address(0x0));
    }

    function canAddressVote(address votersAddress) public view returns(bool) {
        return (votersAddresses[votersAddress] == 1);
    }

}
