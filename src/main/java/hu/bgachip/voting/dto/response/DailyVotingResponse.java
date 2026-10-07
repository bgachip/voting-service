package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DailyVotingResponse(
        @JsonProperty("szavazasok")
        List<DailyVotingItemResponse> votings
) {
}