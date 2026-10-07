package hu.bgachip.voting.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record VoteResponse(
        @JsonProperty("szavazat")
        String vote
) {
}