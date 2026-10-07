package hu.bgachip.voting.domain.enums;

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
}
