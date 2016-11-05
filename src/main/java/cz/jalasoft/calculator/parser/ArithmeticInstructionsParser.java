package cz.jalasoft.calculator.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * A parser of arithmetical instructions from input stream or reader.
 *
 * The parser understands a format where every line consists of:
 * <ul>
 *  <li>Name of operation - add, subtract, divide, multiply</li>
 *  <li>operand - an integer number</li>
 * </ul>
 * The input lines must be terminated by a special line - word apply
 * followed by an integer that servers as a first number of the arithmetical
 * operations.
 *
 * Example:
 *
 * {@code
 * add 5
 * subtract 3
 * multiply 2
 * divide 90
 * apply 6
 * }
 *
 * this is equivalent to 6 + 5 - 3 * 2 / 90
 *
 * <strong>Warning: priorities of operators are not taken into account</strong>
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public interface ArithmeticInstructionsParser {

    /**
     * Registers a listener of parsing.
     * @param listener must not be null
     * @throws IllegalArgumentException if listener is null
     */
    void registerListener(ArithmeticInstructionsParserListener listener);

    /**
     * Start parsing arithmetical instructions from a given input stream.
     *
     * <strong>This method closes the stream after parsing.</strong>
     *
     * @param stream must not be null
     * @throws IOException if an error occurred during reading instructions from the stream
     * @throws IllegalArgumentException if stream is null
     */
    void parse(InputStream stream) throws IOException;

    /**
     * Start parsing arithmetical instructions from a given reader.
     *
     * <strong>This method closes the reader after parsing.</strong>
     *
     * @param reader must not be null
     * @throws IOException if an error occurred during reading instructions from the reader
     * @throws IllegalArgumentException if reader is null
     */
    void parse(Reader reader) throws IOException;
}
