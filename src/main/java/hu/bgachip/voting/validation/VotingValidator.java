package hu.bgachip.voting.validation;

import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.request.VoteRequest;
import hu.bgachip.voting.exception.VotingValidationException;
import hu.bgachip.voting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VotingValidator {

    private final VotingRepository votingRepository;

    public void validateForCreation(CreateVotingRequest request) {
        validateDateTime(request);
        validatePresidentVote(request);
        validateDuplicateVotes(request);
    }

    private void validateDateTime(CreateVotingRequest request) {
        if (votingRepository.existsByDateTime(request.dateTime())) {
            throw new VotingValidationException(
                    "A voting already exists at the specified date and time."
            );
        }
    }

    private void validatePresidentVote(CreateVotingRequest request) {
        boolean presidentHasVoted = request.votes().stream()
                .anyMatch(vote ->
                        vote.representative().equals(request.president())
                );

        if (!presidentHasVoted) {
            throw new VotingValidationException(
                    "The president must have a vote."
            );
        }
    }

    private void validateDuplicateVotes(CreateVotingRequest request) {
        Set<String> representatives = new HashSet<>();

        boolean duplicateExists = request.votes().stream()
                .map(VoteRequest::representative)
                .anyMatch(representative ->
                        !representatives.add(representative)
                );

        if (duplicateExists) {
            throw new VotingValidationException(
                    "A representative can vote only once."
            );
        }
    }
}