package cz.jalasoft.calculator.formula.postfix;

import cz.jalasoft.calculator.formula.FormulaSegment;
import cz.jalasoft.calculator.formula.Operand;
import cz.jalasoft.calculator.formula.Operator;

import java.util.LinkedList;

/**
 * A builder of {@link PostfixFormulaBuilder} allowing building
 * postfix formula by adding operators and operands to the or beginning of
 * the postfix form of formula.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public final class PostfixFormulaBuilder<T> {

    private LinkedList<FormulaSegment<T>> segments;

    PostfixFormulaBuilder() {
        segments = new LinkedList<>();
    }

    public PostfixFormulaBuilder<T> end(Operator<T> operator) {
        segments.offerLast(operator);
        return this;
    }

    public PostfixFormulaBuilder<T> end(Operand<T> operand) {
        segments.offerLast(operand);
        return this;
    }

    public PostfixFormulaBuilder<T> beginning(Operand<T> operand) {
        segments.offerFirst(operand);
        return this;
    }

    public PostfixFormula<T> formula() {
        //TODO here a validation of the segments should be
        //performed in order to detect expected mixing
        //operators and operands (operand, operand, operator, operand, operator.....)
        return new PostfixFormula<>(segments);
    }
}
