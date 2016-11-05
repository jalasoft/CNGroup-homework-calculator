package cz.jalasoft.calculator.formula;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface Operator<T> extends FormulaSegment {

    T apply(T leftOperand, T righOperand);
}
