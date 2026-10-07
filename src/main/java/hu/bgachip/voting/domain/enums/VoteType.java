package hu.bgachip.voting.domain.enums;

import java.util.Arrays;

public enum VoteType {
    YES("i"),
    NO("n"),
    ABSTAIN("t");

    private final String code;

    VoteType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static VoteType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid vote type code: " + code
                        )
                );
    }
}
