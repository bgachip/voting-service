package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record DailyVotingItemResponse(

        @JsonProperty("idopont")
        Instant dateTime,

        @JsonProperty("targy")
        String subject,

        @JsonProperty("tipus")
        String type,

        @JsonProperty("eljaras")
        String procedure,

        @JsonProperty("elnok")
        String president,

        @JsonProperty("eredmeny")
        String result,

        @JsonProperty("kepviselokSzama")
        int representativeCount,

        @JsonProperty("szavazatok")
        List<VoteResponseItem> votes
) {
}