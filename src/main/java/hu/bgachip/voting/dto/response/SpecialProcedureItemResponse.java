package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpecialProcedureItemResponse(

        @JsonProperty("eljaras")
        String procedure,

        @JsonProperty("eredmeny")
        String result,

        @JsonProperty("szam")
        long count
) {
}