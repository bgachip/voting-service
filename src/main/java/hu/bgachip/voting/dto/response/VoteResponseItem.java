package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoteResponseItem(

        @JsonProperty("kepviselo")
        String representative,

        @JsonProperty("szavazat")
        String vote
) {
}