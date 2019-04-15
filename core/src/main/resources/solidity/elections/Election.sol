pragma solidity ^0.5.4;

contract ElectionMaster {
    function addElection(address electionAddress,
                        string memory electionName,
                        address organizerAddress) public {}

    function canAddressDeployContract(address organizerAddress) public view returns(bool) {}

    function canAddressVote(address votersAddress) public view returns(bool) {}
}

contract Election {

    struct Candidate {
        uint8 id;
        bytes32 name;
        bool isValue;
    }

    ElectionMaster private electionMaster;
    Candidate[] private candidates;
    mapping(uint8 => uint128) private votes;
    mapping(address => uint8) private voters;
    string private electionName = "Derp";

    constructor(address masterContractAddress) public {
        electionMaster = ElectionMaster(masterContractAddress);
        bool canDeploy = electionMaster.canAddressDeployContract(msg.sender);
        require(canDeploy == true, "Organizer permissions required to deploy a contract.");
        electionMaster.addElection(address(this), electionName, msg.sender);

        candidates.push(Candidate(0, "Gigel", true));
        candidates.push(Candidate(1, "Dorel", true));
    }

    modifier personAbleToVote(uint8 candidateId) {
        require(electionMaster.canAddressVote(msg.sender), "Address is not registered for voting permissions.");
        require(voters[msg.sender] == 0, "This address has already voted.");
        require(candidates[candidateId].isValue, "Candidate not found.");
        _;
    }

    function vote(uint8 candidateId) public personAbleToVote(candidateId) {
        votes[candidateId] += 1;
        voters[msg.sender] = 1;
    }

     function getCandidates() public view returns(bytes32[] memory) {
        bytes32[] memory candidatesNames = new bytes32[](candidates.length);
        for (uint i = 0; i < candidates.length; i++) {
            candidatesNames[i] = candidates[i].name;
        }
        return candidatesNames;
    }

    function getResultsForCandidate(uint8 candidateId) public view returns(uint128) {
        return votes[candidateId];
    }

    function canAddressVote(address votersAddress) public view returns(bool) {
        return electionMaster.canAddressVote(votersAddress);
    }
}
