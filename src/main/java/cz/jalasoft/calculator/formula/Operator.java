package cz.jalasoft.calculator.formula;

/**
 * A type of formula segment representing an operation.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface Operator<T> extends FormulaSegment {

    /**
     * Performs an operation for two operand.
     * @param leftOperand must not be null
     * @param rightOperand must not be null
     * @return never null
     * @throws IllegalArgumentException if leftOperand or rightOperand is null
     */
    T apply(T leftOperand, T rightOperand);
}
