package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VotingResultResponse(

        @JsonProperty("eredmeny")
        String result,

        @JsonProperty("kepviselokSzama")
        int representativeCount,

        @JsonProperty("igenekSzama")
        long yesCount,

        @JsonProperty("nemekSzama")
        long noCount,

        @JsonProperty("tartozkodasokSzama")
        long abstainCount
) {
}