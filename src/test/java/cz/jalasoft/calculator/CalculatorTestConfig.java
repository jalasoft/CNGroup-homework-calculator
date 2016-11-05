package cz.jalasoft.calculator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-05.
 */
@Configuration
@ComponentScan(
        basePackages = "cz.jalasoft.calculator",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Main.class),
        }
)
public class CalculatorTestConfig {
}
