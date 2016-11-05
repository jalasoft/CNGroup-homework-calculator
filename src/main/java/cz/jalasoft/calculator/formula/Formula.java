package cz.jalasoft.calculator.formula;

/**
 * An abstraction of any formula - arithmetic, boolean, algebraic or any other.
 *
 * @param <T> a type of value after evaluation of the formula.
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface Formula<T> {

    /**
     * Performs a process of minimizing the formula to a result value.
     * @return
     */
    T evaluate();
}
