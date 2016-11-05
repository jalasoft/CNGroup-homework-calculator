package cz.jalasoft.calculator.parser;

import java.util.Optional;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-04.
 */
public enum InstructionType {
    ADD("add", "+"), SUBTRACT("subtract", "-"), DIVIDE("divide", "/"), MULTIPLY("multiply", "*");

    private final String token;
    private final String sign;

    InstructionType(String token, String sign) {
        this.token = token;
        this.sign = sign;
    }

    public String token() {
        return token;
    }

    public String sign() {
        return sign;
    }

    public static Optional<InstructionType> fromToken(String token) {
        for(InstructionType instruction : values()) {
            if (instruction.token().equals(token)) {
                return Optional.of(instruction);
            }
        }
        return Optional.empty();
    }
}
