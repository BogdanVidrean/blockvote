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
        voters[0xCA56BdFF4aB30b96B4F618e718B88C7a1721ECC3] = 1;
        voters[0xA19b33F09A8E09d6f7D9330115038a87fe331878] = 1;
        voters[0x480AC6bC8E492D7840c5EF362Dc90Ff82A704Ad1] = 1;
        voters[0x32E33d20d2a5d0D05F7C088AAaB8f3e7A7F5F4fD] = 1;
        voters[0x3ae77c608E84e349a4312Da6Bb7bCb413755765b] = 1;
        voters[0x52B4BF9844edf3956d6E860cF92bC2CD224164CC] = 1;
        voters[0xf06bc4Dd7BC1738a24c473859C391Bcf81c0f0aE] = 1;

        
        candidates[0] = Candidate(0, "Gigel", true);
        candidates[1] = Candidate(1, "Dorel", true);
    }

    modifier personAbleToVote(uint8 candidateId) {
        require(voters[msg.sender] == 1, "Person is not registered for this vote or already voted.");
        require(candidates[candidateId].isValue, "Candidate not found.");
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
