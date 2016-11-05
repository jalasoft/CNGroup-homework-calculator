package cz.jalasoft.calculator;

import cz.jalasoft.calculator.formula.Formula;
import cz.jalasoft.calculator.parser.ArithmeticInstructionsParser;
import cz.jalasoft.calculator.parser.InvalidInstructionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //---------------------------------------------------------
    //INSTANCE SCOPE
    //---------------------------------------------------------

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplicationArguments arguments;

    @Override
    public void run(String... strings) {

        List<String> files = arguments.getOptionValues("input");
        if (files.isEmpty()) {
            exit(1);
        }

        if (files.size() > 1) {
            //TODO warn
        }

        String file = files.get(0);

        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            //TODO
            exit(1);
        }

        try {
            Number result = process(resource.getInputStream());
            exit(0);
        } catch (IOException exc) {

            exit(2);
        } catch (InvalidInstructionsException exc) {
            //TODO
            exit(3);
        }
    }

    private Number process(InputStream input) throws IOException, InvalidInstructionsException {
        PostfixFormulaParserListener listener = new PostfixFormulaParserListener();

        ArithmeticInstructionsParser parser = new ArithmeticInstructionsParser();
        parser.registerListener(listener);
        parser.parse(input);

        Formula<Number> formula = listener.formula();
        Number result = formula.evaluate();

        System.out.println("Result is " + result);

        return result;
    }

    private void exit(int code) {
        SpringApplication.exit(context, () -> code);
    }
}
