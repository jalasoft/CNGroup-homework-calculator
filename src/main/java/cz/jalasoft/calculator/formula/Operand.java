package cz.jalasoft.calculator.formula;

/**
 * A formula segment representing an operand of a binary formula.
 *
 * <p>
 *     This is class is a template of value objects.
 * </p>
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public final class Operand<T> implements FormulaSegment {


    /**
     * Gets an operand of given value.
     * @param value must no be null
     * @param <T> type of operand
     *
     * @return never null
     */
    public static <T> Operand<T> operand(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Operand value must not be null.");
        }
        return new Operand<>(value);
    }

    //------------------------------------------------
    //INSTANCE SCOPE
    //------------------------------------------------

    private final T value;

    Operand(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Operand)) {
            return false;
        }

        Operand that = (Operand) obj;

        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = result * 37 + value.hashCode();

        return result;
    }
}
