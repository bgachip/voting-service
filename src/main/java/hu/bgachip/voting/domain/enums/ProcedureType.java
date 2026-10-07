package hu.bgachip.voting.domain.enums;


public enum ProcedureType {
    NORMAL("n"),
    URGENT("s"),
    EXCEPTIONAL("k"),
    RULE_DEVIATION("e");

    private final String code;

    ProcedureType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
