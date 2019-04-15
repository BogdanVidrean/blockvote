pragma solidity ^0.5.4;
contract ElectionsMaster {

    address private ownerMasterAddress = msg.sender;

    address[] private elections;
    mapping(address => string) private electionsNames;

    mapping(address => uint8) private organizersMapping;

    mapping(address => uint8) private voters;

    constructor() public {
        organizersMapping[msg.sender] = 1;
        voters[msg.sender] = 1;
    }

    // Modifiers

    modifier isMasterAccount(address senderAddress) {
        require(senderAddress == ownerMasterAddress, "Master privileges required");
        _;
    }

    modifier isOrganizer(address senderAddress) {
        require(organizersMapping[senderAddress] != 0, "Organizer privilleges required");
        _;
    }

    // Master Account API

    function changeOwnerMasterAccount(address newOwnerMasterAccount) public isMasterAccount(msg.sender) {
        ownerMasterAddress = newOwnerMasterAccount;
        organizersMapping[newOwnerMasterAccount] = 1;
    }

    function addOrganizer(address newOrganizer) public isMasterAccount(msg.sender) {
        organizersMapping[newOrganizer] = 1;
    }

    // Organizer API

    function addElection(address electionAddress,
                        string memory electionName,
                        address organizerAddress) public isOrganizer(organizerAddress) {
        elections.push(electionAddress);
        electionsNames[electionAddress] = electionName;
    }

    function addVoter(address voterAddress) public isOrganizer(msg.sender) {
        voters[voterAddress] = 1;
    }

    function getElectionAddresses() public view returns(address[] memory) {
        return elections;
    }

    function canAddressDeployContract(address organizerAddress) public view returns(bool) {
        return (organizersMapping[organizerAddress] == 1);
    }

    //  Voter API
    function canAddressVote(address votersAddress) public view returns(bool) {
        return (voters[votersAddress] == 1);
    }

}
