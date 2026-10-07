package hu.bgachip.voting.service;

import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.response.CreateVotingResponse;
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

    @Transactional
    public CreateVotingResponse createVoting(CreateVotingRequest request) {

        votingValidator.validateForCreation(request);

        String votingId = votingIdGenerator.generate();

        Voting voting = votingMapper.toEntity(request, votingId);

        votingRepository.save(voting);

        return new CreateVotingResponse(votingId);
    }
}