package hu.bgachip.voting.service;

import hu.bgachip.voting.domain.entity.Vote;
import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.domain.enums.VotingType;
import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.response.*;
import hu.bgachip.voting.exception.ResourceNotFoundException;
import hu.bgachip.voting.generator.VotingIdGenerator;
import hu.bgachip.voting.mapper.VotingMapper;
import hu.bgachip.voting.repository.VotingRepository;
import hu.bgachip.voting.validation.VotingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotingService {

    private final VotingRepository votingRepository;
    private final VotingValidator votingValidator;
    private final VotingMapper votingMapper;
    private final VotingIdGenerator votingIdGenerator;
    private final VotingResultService votingResultService;

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

    @Transactional(readOnly = true)
    public DailyVotingResponse getDailyVotings(LocalDate date) {
        Instant from = date
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);

        Instant to = date
                .plusDays(1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);

        List<DailyVotingItemResponse> votings = votingRepository
                .findAllByDateRange(from, to)
                .stream()
                .map(this::toDailyVotingResponse)
                .toList();

        return new DailyVotingResponse(votings);
    }

    @Transactional(readOnly = true)
    public ParticipationAverageResponse getParticipationAverage(
            Instant from,
            Instant to
    ) {
        List<Voting> votings =
                votingRepository.findAllByDateRangeExcludingType(
                        from,
                        to,
                        VotingType.PRESENCE
                );

        Map<String, Long> participationByRepresentative = votings.stream()
                .flatMap(voting -> voting.getVotes().stream())
                .collect(Collectors.groupingBy(
                        Vote::getRepresentative,
                        Collectors.counting()
                ));

        double average = participationByRepresentative.values()
                .stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        average = Math.round(average * 100.0) / 100.0;

        return new ParticipationAverageResponse(average);
    }

    @Transactional
    public CreateVotingResponse createVoting(CreateVotingRequest request) {

        votingValidator.validateForCreation(request);

        String votingId = votingIdGenerator.generate();

        Voting voting = votingMapper.toEntity(request, votingId);

        votingRepository.save(voting);

        return new CreateVotingResponse(votingId);
    }

    private DailyVotingItemResponse toDailyVotingResponse(Voting voting) {
        VotingResultResponse result =
                votingResultService.calculateResult(voting);

        List<VoteResponseItem> votes = voting.getVotes()
                .stream()
                .map(vote -> new VoteResponseItem(
                        vote.getRepresentative(),
                        vote.getVote().getCode()
                ))
                .toList();

        return new DailyVotingItemResponse(
                voting.getDateTime(),
                voting.getSubject(),
                voting.getType().getCode(),
                voting.getProcedure() == null
                        ? null
                        : voting.getProcedure().getCode(),
                voting.getPresident(),
                result.result(),
                result.representativeCount(),
                votes
        );
    }
}