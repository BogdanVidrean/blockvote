pragma solidity ^0.5.4;
contract Election {
    
    struct Candidate {
        uint8 id;
        bytes32 name;
        bool isValue;
    }
    
    mapping(address=>uint8) private voters;
    Candidate[2] private candidates;
    mapping(uint8=>uint128) private votes;
    
    constructor() public {
        voters[0x8976fC040A9F2B22974E598387f40fF8e1125E7b] = 1;
        
        candidates[0] = Candidate(0, "Gigel", true);
        candidates[1] = Candidate(1, "Dorel", true);
    }

    modifier personAbleToVote(uint8 candidateId) {
        require(voters[msg.sender] == 1, "Person is not registered for this vote or already voted.");
        require(!candidates[candidateId].isValue, "Candidate not found.");
        _;
    }
    
    function vote(uint8 candidateId) public personAbleToVote(candidateId) {
        votes[candidateId] += 1;
        voters[msg.sender] = 2;
    }
    
    function getCandidates() public view returns(bytes32[2] memory) {
        bytes32[2] memory candidatesNames;
        for (uint i = 0; i < candidates.length; i++) {
            candidatesNames[i] = candidates[i].name;
        }
        return candidatesNames;
    }

    function getResultsForCandidate(uint8 candidateId) public view returns(uint128) {
        return votes[candidateId];
    }

    function derp() public view returns(uint8) {
        return 1;
    }

}
