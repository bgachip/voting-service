package hu.bgachip.voting.service;

import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.domain.enums.VoteType;
import hu.bgachip.voting.domain.enums.VotingResult;
import hu.bgachip.voting.domain.enums.VotingType;
import hu.bgachip.voting.dto.response.VotingResultResponse;
import hu.bgachip.voting.exception.ResourceNotFoundException;
import hu.bgachip.voting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VotingResultService {

    private static final int TOTAL_REPRESENTATIVES = 200;

    private final VotingRepository votingRepository;

    @Transactional(readOnly = true)
    public VotingResultResponse getResult(String votingId) {

        Voting voting = votingRepository.findByVotingId(votingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Voting not found: " + votingId
                ));

        long yesCount = countVotes(voting, VoteType.YES);
        long noCount = countVotes(voting, VoteType.NO);
        long abstainCount = countVotes(voting, VoteType.ABSTAIN);

        int representativeCount = getRepresentativeCount(voting);

        boolean accepted = isAccepted(
                voting,
                yesCount,
                representativeCount
        );

        VotingResult result = accepted
                ? VotingResult.ACCEPTED
                : VotingResult.REJECTED;

        return new VotingResultResponse(
                result.getCode(),
                representativeCount,
                yesCount,
                noCount,
                abstainCount
        );
    }

    private long countVotes(Voting voting, VoteType voteType) {
        return voting.getVotes()
                .stream()
                .filter(vote -> vote.getVote() == voteType)
                .count();
    }

    private int getRepresentativeCount(Voting voting) {

        if (voting.getType() == VotingType.PRESENCE) {
            return voting.getVotes().size();
        }

        Voting presenceVoting = votingRepository
                .findFirstByTypeAndDateTimeBeforeOrderByDateTimeDesc(
                        VotingType.PRESENCE,
                        voting.getDateTime()
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No preceding presence voting found."
                ));

        return presenceVoting.getVotes().size();
    }

    private boolean isAccepted(
            Voting voting,
            long yesCount,
            int representativeCount
    ) {
        return switch (voting.getType()) {

            case PRESENCE -> true;

            case SIMPLE_MAJORITY ->
                    yesCount > representativeCount / 2;

            case QUALIFIED_MAJORITY ->
                    yesCount > TOTAL_REPRESENTATIVES / 2;
        };
    }
}