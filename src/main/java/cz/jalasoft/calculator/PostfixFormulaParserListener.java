package cz.jalasoft.calculator;

import cz.jalasoft.calculator.formula.Formula;
import cz.jalasoft.calculator.formula.ArithmeticOperator;
import cz.jalasoft.calculator.formula.Operator;
import cz.jalasoft.calculator.formula.postfix.PostfixFormula;
import cz.jalasoft.calculator.formula.postfix.PostfixFormulaBuilder;
import cz.jalasoft.calculator.parser.ArithmeticInstructionsParserListener;
import cz.jalasoft.calculator.parser.InstructionType;
import cz.jalasoft.calculator.parser.InvalidInstructionsException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static cz.jalasoft.calculator.formula.Operand.operand;
import static cz.jalasoft.calculator.parser.InstructionType.*;

/**
 * A specialized listener of parsing that builds an instance of {@link Formula} based
 * on incoming instructions.
 *
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-04.
 */
public final class PostfixFormulaParserListener implements ArithmeticInstructionsParserListener {

    private static final Map<InstructionType, Operator<Number>> INSTRUCTION_TO_OPERATOR = new EnumMap(InstructionType.class);

    static {
        INSTRUCTION_TO_OPERATOR.put(ADD, ArithmeticOperator.add());
        INSTRUCTION_TO_OPERATOR.put(SUBTRACT, ArithmeticOperator.subtract());
        INSTRUCTION_TO_OPERATOR.put(MULTIPLY, ArithmeticOperator.multiply());
        INSTRUCTION_TO_OPERATOR.put(DIVIDE, ArithmeticOperator.divide());
    }

    /*
    //TODO this could be probably the easier way of implementing
    //the calculator - build expression (40 + 2) and then
    //put it to the javascript engine :-)
    /*
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String foo = "40+2";
        System.out.println(engine.eval(foo));
    */

    private final PostfixFormulaBuilder<Number> formulaBuilder = PostfixFormula.newFormula();
    private final List<String> errors = new ArrayList<>();

    @Override
    public void onArithmeticInstruction(InstructionType instructionType, int number) {
        Operator<Number> operator = INSTRUCTION_TO_OPERATOR.get(instructionType);

        if (operator == null) {
            //programmatic bug - forgotten mapping
            throw new RuntimeException("Unknown type of operation: " + operator);
        }

        formulaBuilder.end(operand(number));
        formulaBuilder.end(operator);
    }

    @Override
    public void onApplyInstruction(int number) {
        formulaBuilder.beginning(operand(number));
    }

    @Override
    public void onInvalidInstruction(String line, String description) {
        errors.add("An error on line '" + line + "'. Reason: " + description);
    }

    public Formula<Number> formula() throws InvalidInstructionsException {
        if (!errors.isEmpty()) {
            throw new InvalidInstructionsException(errors);
        }
        return formulaBuilder.formula();
    }
}
