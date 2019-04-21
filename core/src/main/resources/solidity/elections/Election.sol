pragma solidity ^0.5.4;

contract ElectionMaster {
    function addElection(address electionAddress,
        string memory electionName,
        address organizerAddress) public {}

    function canAddressDeployContract(address organizerAddress) public view returns(bool) {}

    function canAddressVote(address votersAddress) public view returns(bool) {}
}

contract Election {

    struct Option {
        uint8 id;
        bytes32 name;
        bool isValue;
    }

    ElectionMaster private electionMaster;
    Option[] private options;
    mapping(uint8 => uint128) private votes;
    mapping(address => uint8) private voters;
    string private electionName;

    constructor(address masterContractAddress, string memory nameOfElection, bytes32[] memory initialOptions) public {
        electionMaster = ElectionMaster(masterContractAddress);
        bool canDeploy = electionMaster.canAddressDeployContract(msg.sender);
        require(canDeploy == true, "Organizer permissions required to deploy a contract.");
        electionMaster.addElection(address(this), electionName, msg.sender);

        electionName = nameOfElection;
        for (uint8 i = 0; i < options.length; i++) {
            options.push(Option(i, initialOptions[i], true));
        }
    }

    modifier personAbleToVote(uint8 optionId) {
        require(electionMaster.canAddressVote(msg.sender), "Address is not registered for voting permissions.");
        require(voters[msg.sender] == 0, "This address has already voted.");
        require(options[optionId].isValue, "Option not found.");
        _;
    }

    function vote(uint8 optionId) public personAbleToVote(optionId) {
        votes[optionId] += 1;
        voters[msg.sender] = 1;
    }

    function getOptions() public view returns (bytes32[] memory) {
        bytes32[] memory optionsName = new bytes32[](options.length);
        for (uint i = 0; i < options.length; i++) {
            optionsName[i] = options[i].name;
        }
        return optionsName;
    }

    function getResultsForOption(uint8 optionId) public view returns (uint128) {
        return votes[optionId];
    }

    function canAddressVote(address votersAddress) public view returns(bool) {
        return electionMaster.canAddressVote(votersAddress);
    }
}
