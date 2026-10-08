package hu.bgachip.voting.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public record CreateVotingRequest(

        @JsonProperty("idopont")
        @NotNull
        Instant dateTime,

        @JsonProperty("targy")
        @NotBlank
        String subject,

        @JsonProperty("tipus")
        @NotBlank
        String type,

        @JsonProperty(value = "eljaras")
        String procedure,

        @JsonProperty("elnok")
        @NotBlank
        String president,

        @JsonProperty("szavazatok")
        @NotEmpty
        List<@NotNull @Valid VoteRequest> votes

) {
}