package cz.jalasoft.calculator.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This implementation of the parser takes advantage of
 * regular expressions to distinguish particular instructions.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-05.
 */
public final class RegexBasedArithmeticInstructionsParser implements ArithmeticInstructionsParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexBasedArithmeticInstructionsParser.class);

    private static final Pattern ARITHMETIC_INSTRUCTION_PATTERN = Pattern.compile("(add||subtract|multiply|divide|apply) (\\-?\\d+)");


    private final AggregatingInstructionListener listenerAggregator = new AggregatingInstructionListener();

    public void registerListener(ArithmeticInstructionsParserListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null.");
        }

        LOGGER.debug("Registering a listener {}", listener.getClass());

        listenerAggregator.addListener(listener);
    }

    public void parse(InputStream stream) throws IOException {
        parse(new InputStreamReader(stream));
    }

    public void parse(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null.");
        }

        LOGGER.debug("Starting parsing...");

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;

            while((line = bufferedReader.readLine()) != null) {
                String trimmed = line.trim();
                LOGGER.debug("Parsing a line >{}<", trimmed);

                parseLine(trimmed);
            }
        }
    }

    private void parseLine(String line) {
        String lineInLowerCase = line.toLowerCase();

        Matcher matcher = ARITHMETIC_INSTRUCTION_PATTERN.matcher(lineInLowerCase);

        if (!matcher.matches()) {
            notifyInvalidInstruction(line);
            return;
        }

        String operation = matcher.group(1);
        String operandString = matcher.group(2);
        int operand = Integer.parseInt(operandString);

        if (operation.equals("apply")) {
            notifyApplyInstruction(operand);
        } else {
            notifyArithmeticInstruction(operation, operand);
        }
    }

    private void notifyInvalidInstruction(String line) {
        try {
            listenerAggregator.proxy().onInvalidInstruction(line, "The instruction does not match pattern '" + ARITHMETIC_INSTRUCTION_PATTERN.pattern() + "'");
        } catch (Exception exc) {
            LOGGER.error("An error occurred inside instruction parser listener.", exc);
        }
    }

    private void notifyArithmeticInstruction(String operation, int operand) {
        Optional<InstructionType> maybeInstructionType = InstructionType.fromToken(operation);

        if (!maybeInstructionType.isPresent()) {
            //programmatic bug
            throw new RuntimeException("No instruction type available for operation '" + operation + "'.");
        }

        InstructionType instructionType = maybeInstructionType.get();

        try {
            listenerAggregator.proxy().onArithmeticInstruction(instructionType, operand);
        } catch (Exception exc) {
            LOGGER.error("An error occurred inside instruction parser listener.", exc);
        }
    }

    private void notifyApplyInstruction(int operand) {
        try {
            listenerAggregator.proxy().onApplyInstruction(operand);
        } catch (Exception exc) {
            LOGGER.error("An error occurred inside instruction parser listener.", exc);
        }
    }
}
