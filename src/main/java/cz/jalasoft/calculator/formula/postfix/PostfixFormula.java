package cz.jalasoft.calculator.formula.postfix;

import cz.jalasoft.calculator.formula.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A specialized formula that represents operator and
 * operands in an postfix form. For example: 2,4,+,8,*
 * which is equivalent following traditinal infix form:
 * 2 + 4 * 8 (not considering priority of some operators)
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public final class PostfixFormula<T> implements BinaryOperationFormula<T> {

    public static <T> PostfixFormulaBuilder<T> newFormula() {
        return new PostfixFormulaBuilder<T>();
    }

    //---------------------------------------------------------
    //INSTANCE SCOPE
    //---------------------------------------------------------

    private final LinkedList<FormulaSegment<T>> segments;


    PostfixFormula(LinkedList<FormulaSegment<T>> segments) {
        this.segments = segments;
    }

    @Override
    public Iterator<FormulaSegment<T>> iterator() {
        return segments.iterator();
    }

    @Override
    public T evaluate() {
        while(segments.size() > 1 ) {
            applyOperation();
        }

        Operand<T> result = (Operand<T>) segments.pop();
        return result.value();
    }

    private void applyOperation() {
        Operand<T> operand1 = (Operand<T>) segments.pop();
        Operand<T> operand2 = (Operand<T>) segments.pop();
        Operator<T> operator = (Operator<T>) segments.pop();

        T result = operator.apply(operand1.value(), operand2.value());
        segments.push(Operand.operand(result));
    }
}
