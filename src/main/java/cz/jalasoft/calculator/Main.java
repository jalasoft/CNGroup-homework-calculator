package cz.jalasoft.calculator;

import cz.jalasoft.calculator.parser.InvalidInstructionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    //---------------------------------------------------------
    //INSTANCE SCOPE
    //---------------------------------------------------------

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplicationArguments arguments;

    @Autowired
    private Calculator calculator;

    @Override
    public void run(String... strings) {

        List<String> files = arguments.getOptionValues("input");
        if (files == null || files.isEmpty()) {
            System.out.println("No file specified. Use option --input to specify file containing arithmetic instructions.");
            exit(1);
            return;
        }

        String file = files.get(0);

        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            System.out.println("Specified input file '" + file + "' does not exists.");
            exit(1);
            return;
        }

        LOGGER.debug("Input file with arithmetical instructions obtained: {}", file);

        try (InputStream instructionsStream = resource.getInputStream()) {
            Number result = calculator.calculate(instructionsStream);
            System.out.println("Result of operation is " + result);
            exit(0);
        } catch (IOException exc) {
            System.out.println("An error occurred during reading arithmetical instructions: " + exc.getMessage());
            exit(2);
        } catch (InvalidInstructionsException exc) {
            System.out.println(exc.getMessage());
            exit(3);
        }
    }

    private void exit(int code) {
        LOGGER.debug("Exiting with code {}", code);
        SpringApplication.exit(context, () -> code);
    }
}
