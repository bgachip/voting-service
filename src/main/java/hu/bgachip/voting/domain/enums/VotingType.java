package hu.bgachip.voting.domain.enums;

import java.util.Arrays;

public enum VotingType {
    PRESENCE("j"),
    SIMPLE_MAJORITY("e"),
    QUALIFIED_MAJORITY("m");

    private final String code;

    VotingType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static VotingType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid voting type code: " + code
                        )
                );
    }
}
