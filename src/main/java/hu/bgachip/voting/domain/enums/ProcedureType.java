package hu.bgachip.voting.domain.enums;


import java.util.Arrays;

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

    public static ProcedureType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid procedure type code: " + code
                        )
                );
    }
}
