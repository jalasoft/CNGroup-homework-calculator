package cz.jalasoft.calculator;

import cz.jalasoft.calculator.parser.ArithmeticInstructionsParser;
import cz.jalasoft.calculator.parser.ArithmeticInstructionsParserListener;

import cz.jalasoft.calculator.parser.RegexBasedArithmeticInstructionsParser;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static cz.jalasoft.calculator.parser.InstructionType.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public class InstructionParserTest {

    private ArithmeticInstructionsParser parser;

    @BeforeTest
    public void init() {
        parser = new RegexBasedArithmeticInstructionsParser();
    }

    @Test
    public void parserNotifiesListenerMethodsInExpectedOrderAndWithExpectedValues() throws IOException, InvalidInstructionsException {

        ArithmeticInstructionsParserListener listener = Mockito.mock(ArithmeticInstructionsParserListener.class);
        parser.registerListener(listener);

        Reader input = instructions(
                "add 3",
                "multiply 5",
                "subtract 7",
                "divide 2",
                "apply 2");

        parser.parse(input);

        InOrder order = Mockito.inOrder(listener);
        order.verify(listener).onArithmeticInstruction(ADD, 3);
        order.verify(listener).onArithmeticInstruction(MULTIPLY, 5);
        order.verify(listener).onArithmeticInstruction(SUBTRACT, 7);
        order.verify(listener).onApplyInstruction(2);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void parserFailsDueToBullshitInstruction() throws IOException, InvalidInstructionsException {
        ArithmeticInstructionsParserListener listener = Mockito.mock(ArithmeticInstructionsParserListener.class);
        parser.registerListener(listener);

        Reader input = instructions(
                "add 3",
                "multiply 5",
                "subtract 7",
                "this is very bad instruction",
                "apply 2");

        parser.parse(input);

        InOrder order = Mockito.inOrder(listener);
        order.verify(listener).onArithmeticInstruction(ADD, 3);
        order.verify(listener).onArithmeticInstruction(MULTIPLY, 5);
        order.verify(listener).onArithmeticInstruction(SUBTRACT, 7);
        order.verify(listener).onInvalidInstruction(eq("this is very bad instruction"), any());
        order.verify(listener).onApplyInstruction(2);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void parserFailsDueToEmptyLine() throws IOException, InvalidInstructionsException {
        ArithmeticInstructionsParserListener listener = Mockito.mock(ArithmeticInstructionsParserListener.class);
        parser.registerListener(listener);

        Reader input = instructions(
                "multiply 67",
                "subtract 23",
                "add 7",
                " ",
                "apply 2");

        parser.parse(input);

        InOrder order = Mockito.inOrder(listener);
        order.verify(listener).onArithmeticInstruction(MULTIPLY, 67);
        order.verify(listener).onArithmeticInstruction(SUBTRACT, 23);
        order.verify(listener).onArithmeticInstruction(ADD, 7);
        order.verify(listener).onInvalidInstruction(eq(""), any());
        order.verify(listener).onApplyInstruction(2);
        order.verifyNoMoreInteractions();
    }

    private Reader instructions(String... lines) {
        String instructions = Arrays.stream(lines).collect(Collectors.joining("\n"));
        return new StringReader(instructions);
    }
}
