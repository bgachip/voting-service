package hu.bgachip.voting.mapper;

import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.domain.enums.ProcedureType;
import hu.bgachip.voting.domain.enums.VoteType;
import hu.bgachip.voting.domain.enums.VotingType;
import hu.bgachip.voting.dto.request.CreateVotingRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
public class VotingMapper {

    public Voting toEntity(
            CreateVotingRequest request,
            String votingId
    ) {
        Voting voting = new Voting(
                votingId,
                request.dateTime(),
                request.subject(),
                VotingType.fromCode(request.type()),
                request.procedure() == null
                        ? null
                        : ProcedureType.fromCode(request.procedure()),
                request.president()
        );

        request.votes().forEach(vote ->
                voting.addVote(
                        vote.representative(),
                        VoteType.fromCode(vote.vote())
                )
        );

        return voting;
    }
}