package cz.jalasoft.calculator.parser;

import java.io.*;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public final class ArithmeticInstructionsParser {

    private static final Pattern ARITHMETIC_INSTRUCTION_PATTERN = Pattern.compile("(add||subtract|multiply|divide|apply) (\\d+)");

    private final AggregatingInstructionListener listenerAggregator = new AggregatingInstructionListener();

    public void registerListener(ArithmeticInstructionsParserListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null.");
        }

        listenerAggregator.addListener(listener);
    }

    public void parse(InputStream stream) throws IOException {
        parse(new InputStreamReader(stream));
    }

    public void parse(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null.");
        }

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;

            while((line = bufferedReader.readLine()) != null) {
                parseLine(line);
            }
        }
    }

    private void parseLine(String line) {
        String lineInLowerCase = line.toLowerCase();

        Matcher matcher = ARITHMETIC_INSTRUCTION_PATTERN.matcher(lineInLowerCase);

        if (!matcher.matches()) {
            listenerAggregator.proxy().onInvalidInstruction(line, "The instruction does not match pattern '" + ARITHMETIC_INSTRUCTION_PATTERN.pattern() + "'");
            return;
        }

        String operation = matcher.group(1);
        int operand = Integer.parseInt(matcher.group(2));

        if (operation.equals("apply")) {
            notifyApplyInstruction(operand);
        } else {
            notifyArithmeticInstruction(operation, operand);
        }
    }

    private void notifyArithmeticInstruction(String operation, int operand) {
        Optional<InstructionType> maybeInstructionType = InstructionType.fromToken(operation);

        if (!maybeInstructionType.isPresent()) {
            //programmatic bug
            throw new RuntimeException("No instruction type available for operation '" + operation + "'.");
        }

        InstructionType instructionType = maybeInstructionType.get();


        listenerAggregator.proxy().onArithmeticInstruction(instructionType, operand);
    }

    private void notifyApplyInstruction(int operand) {
        listenerAggregator.proxy().onApplyInstruction(operand);
    }
}
