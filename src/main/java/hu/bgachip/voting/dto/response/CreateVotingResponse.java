package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateVotingResponse(

        @JsonProperty("szavazasId")
        String votingId

) {
}