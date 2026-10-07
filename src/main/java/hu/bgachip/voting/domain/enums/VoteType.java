package hu.bgachip.voting.domain.enums;

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
}
