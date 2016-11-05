package cz.jalasoft.calculator.config;

import cz.jalasoft.calculator.Calculator;
import cz.jalasoft.calculator.parser.ArithmeticInstructionsParser;
import cz.jalasoft.calculator.parser.RegexBasedArithmeticInstructionsParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-05.
 */
@Configuration
public class CalculatorConfig {

    @Bean
    public Calculator calculator() {
        Supplier<ArithmeticInstructionsParser> parserFactory = () -> new RegexBasedArithmeticInstructionsParser();

        return new Calculator(parserFactory);
    }
}
