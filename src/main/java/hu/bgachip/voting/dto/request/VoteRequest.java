package hu.bgachip.voting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record VoteRequest(

        @JsonProperty("kepviselo")
        @NotBlank
        String representative,

        @JsonProperty("szavazat")
        @NotBlank
        String vote

) {
}