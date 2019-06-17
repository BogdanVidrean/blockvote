pragma solidity ^0.5.9;

contract ElectionMaster {
    function addElection(address electionAddress,
        bytes32 electionName,
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
    uint128[] private votes;
    mapping(address => uint8) private voters;
    bytes32 private electionName;
    uint private startTime;
    uint private endTime;
    bool private isOver;

    constructor(address masterContractAddress, bytes32 nameOfElection, bytes32[] memory initialOptions, uint electionStartTime, uint electionEndTime) public areTimestampsValid(electionStartTime, electionEndTime) {
        electionMaster = ElectionMaster(masterContractAddress);
        bool canDeploy = electionMaster.canAddressDeployContract(msg.sender);
        require(canDeploy == true, "Organizer permissions required to deploy a contract.");
        electionMaster.addElection(address(this), nameOfElection, msg.sender);

        electionName = nameOfElection;
        for (uint8 i = 0; i < initialOptions.length; i++) {
            options.push(Option(i, initialOptions[i], true));
            votes.push(0);
        }
        startTime = electionStartTime;
        endTime = electionEndTime;
        isOver = false;
    }

    modifier areTimestampsValid(uint startTimeToValidate, uint endTimeToValidate) {
        require(now < startTimeToValidate, "The election cannot start in the past");
        require(startTimeToValidate < endTimeToValidate, "The start time of the election must be before the end time.");
        _;
    }

    modifier personAbleToVote(uint8 optionId) {
        require(electionMaster.canAddressVote(msg.sender), "Address is not registered for voting permissions.");
        require(voters[msg.sender] == 0, "This address has already voted.");
        require(options[optionId].isValue, "Option not found.");
        require(now > startTime, "The election didn't start yet.");
        require(now < endTime, "The election is finished.");
        _;
    }

    modifier isElectionOver() {
        require(isOver == true, "The election is not over yet.");
        _;
    }

    modifier isTimeOver() {
        require(now > endTime, "The time is not over yet.");
        _;
    }

    modifier isOrganizer() {
        require(electionMaster.canAddressDeployContract(msg.sender), "Only organizers can end elections.");
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

    function getResults() public view isElectionOver returns (uint128[] memory) {
        return votes;
    }

    function canAddressVote(address votersAddress) public view returns(bool) {
        return electionMaster.canAddressVote(votersAddress);
    }

    function endElection() public isTimeOver isOrganizer {
        isOver = true;
    }

    function getStartTime() public view returns (uint) {
        return startTime;
    }

    function getEndTime() public view returns (uint) {
        return endTime;
    }

    function isElectionMarkedOver() public view returns (bool) {
        return isOver;
    }
}
