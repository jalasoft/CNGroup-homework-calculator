package cz.jalasoft.calculator.parser;

/**
 * A listener of a parser of arithmetic instructions.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface ArithmeticInstructionsParserListener {

    void onArithmeticInstruction(InstructionType instructionType, int number);

    /**
     * Invoked when {@code apply <number>} in found
     * @param number
     */
    void onApplyInstruction(int number);

    void onInvalidInstruction(String line, String description);
}
