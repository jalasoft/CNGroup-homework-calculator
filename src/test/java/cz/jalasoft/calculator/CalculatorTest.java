package cz.jalasoft.calculator;

import cz.jalasoft.calculator.parser.InvalidInstructionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-05.
 */
@ContextConfiguration(classes = CalculatorTestConfig.class)
public class CalculatorTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Calculator calculator;

    @Test
    public void calculatorExecutesInstructionFilesExpectedly() throws IOException, InvalidInstructionsException {
        InputStream input = resourceFile("input.txt");

        Number result = calculator.calculate(input);

        assertEquals(result.doubleValue(), 223d);
    }

    private InputStream resourceFile(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }
}
