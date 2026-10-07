package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ParticipationAverageResponse(
        @JsonProperty("atlag")
        double average
) {
}