package hu.bgachip.voting.service;

import hu.bgachip.voting.domain.entity.Vote;
import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.response.CreateVotingResponse;
import hu.bgachip.voting.dto.response.VoteResponse;
import hu.bgachip.voting.exception.ResourceNotFoundException;
import hu.bgachip.voting.generator.VotingIdGenerator;
import hu.bgachip.voting.mapper.VotingMapper;
import hu.bgachip.voting.repository.VotingRepository;
import hu.bgachip.voting.validation.VotingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final VotingValidator votingValidator;
    private final VotingMapper votingMapper;
    private final VotingIdGenerator votingIdGenerator;

    @Transactional(readOnly = true)
    public VoteResponse getVote(
            String votingId,
            String representative
    ) {
        Voting voting = votingRepository.findByVotingId(votingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Voting not found: " + votingId
                ));

        Vote vote = voting.getVotes()
                .stream()
                .filter(v -> v.getRepresentative().equals(representative))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vote not found for representative: " + representative
                ));

        return new VoteResponse(vote.getVote().getCode());
    }

    @Transactional
    public CreateVotingResponse createVoting(CreateVotingRequest request) {

        votingValidator.validateForCreation(request);

        String votingId = votingIdGenerator.generate();

        Voting voting = votingMapper.toEntity(request, votingId);

        votingRepository.save(voting);

        return new CreateVotingResponse(votingId);
    }
}