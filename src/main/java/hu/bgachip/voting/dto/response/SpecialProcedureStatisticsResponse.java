package hu.bgachip.voting.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SpecialProcedureStatisticsResponse(

        @JsonProperty("szavazasok")
        List<SpecialProcedureItemResponse> votings
) {
}