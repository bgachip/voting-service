package hu.bgachip.voting.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VotingResult {

    ACCEPTED("F"),
    REJECTED("U");

    private final String code;
}