package cz.jalasoft.calculator.parser;

/**
 * A listener of a parser of arithmetic instructions.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface ArithmeticInstructionsParserListener {

    /**
     * Invoked by the parser when it encounters an arithmetic instruction.
     * @param instructionType never null
     * @param number
     */
    void onArithmeticInstruction(InstructionType instructionType, int number);

    /**
     * Invoked by the parser when it encounters the apply instruction
     * @param number
     */
    void onApplyInstruction(int number);

    /**
     * Invoked by the parser when it encounters an instruction that is not
     * expected and foes not match any expected ones.
     * @param line might be empty in case an empty line is encountered during parsing
     * @param description a description of the failure, never null or empty.
     */
    void onInvalidInstruction(String line, String description);
}
