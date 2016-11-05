package cz.jalasoft.calculator;

import cz.jalasoft.calculator.formula.Formula;
import cz.jalasoft.calculator.parser.ArithmeticInstructionsParser;
import cz.jalasoft.calculator.parser.InvalidInstructionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * A base class for performing arithmetical operations.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-05.
 */
@Component
public final class Calculator {

    private final Supplier<ArithmeticInstructionsParser> parserFactory;

    @Autowired
    public Calculator(Supplier<ArithmeticInstructionsParser> parserFactory) {
        this.parserFactory = parserFactory;
    }

    /**
     * Calculates arithmetic operations from an input stream. The format of instructions is describes
     * in {@link ArithmeticInstructionsParser}
     *
     * <strong>This method closes the stream after parsing.</strong>
     *
     * @param instructionsStream must not be null
     * @return a numerical result
     * @throws IOException if an error occurred during reading input
     * @throws InvalidInstructionsException if one or more instructions could not be resolved.
     */
    public Number calculate(InputStream instructionsStream) throws IOException, InvalidInstructionsException {
        if (instructionsStream == null) {
            throw new IllegalArgumentException("Instruction stream must not be null.");
        }

        ArithmeticInstructionsParser parser = parserFactory.get();

        PostfixFormulaParserListener listener = new PostfixFormulaParserListener();
        parser.registerListener(listener);
        parser.parse(instructionsStream);

        Formula<Number> formula = listener.formula();

        Number result = formula.evaluate();
        return result;
    }
}
