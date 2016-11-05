package cz.jalasoft.calculator;

import java.util.List;

/**
 *
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-11-03.
 */
public final class InvalidInstructionsException extends Exception {

    private final List<String> messages;

    public InvalidInstructionsException(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String getMessage() {
        return "One or more invalid arithmetic instructions encountered during parsing input: '" + messages + "'.";
    }
}
