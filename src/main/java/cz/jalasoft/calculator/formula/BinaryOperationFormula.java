package cz.jalasoft.calculator.formula;

/**
 * A formula that consists of operators and binary operations.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-04.
 */
public interface BinaryOperationFormula<T> extends Formula<T>, Iterable<FormulaSegment<T>> {

}
