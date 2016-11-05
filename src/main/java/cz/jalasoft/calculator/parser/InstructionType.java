package cz.jalasoft.calculator.parser;

import java.util.Optional;

/**
 * An enum that represents a type of instruction. Providing an instruction token that
 * is expected in an input as well as a human readable mathematical sign of given operation.
 *
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

    /**
     * Gets a name of the operation that is expected in an input during parsing.
     * @return never null or empty
     */
    public String token() {
        return token;
    }

    /**
     * Gets a human readable mathematical sign of the operation.
     * @return never null or empty.
     */
    public String sign() {
        return sign;
    }

    //------------------------------------------------------------
    //STATIC SCOPE
    //------------------------------------------------------------

    /**
     * Provides an instance of the enum for given token.
     * @param token
     * @return empty if the token does not match any existing instruction.
     */
    public static Optional<InstructionType> fromToken(String token) {
        for(InstructionType instruction : values()) {
            if (instruction.token().equals(token)) {
                return Optional.of(instruction);
            }
        }
        return Optional.empty();
    }
}
