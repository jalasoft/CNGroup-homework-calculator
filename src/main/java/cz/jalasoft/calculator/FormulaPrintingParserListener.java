package cz.jalasoft.calculator;

import cz.jalasoft.calculator.parser.ArithmeticInstructionsParserListener;
import cz.jalasoft.calculator.parser.InstructionType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-04.
 */
public final class FormulaPrintingParserListener implements ArithmeticInstructionsParserListener {

    private final StringBuilder buffer;

    public FormulaPrintingParserListener() {
        this.buffer = new StringBuilder();
    }

    @Override
    public void onArithmeticInstruction(InstructionType instruction, int number) {

    }

    @Override
    public void onApplyInstruction(int number) {

    }

    @Override
    public void onInvalidInstruction(String line, String description) {

    }
}
