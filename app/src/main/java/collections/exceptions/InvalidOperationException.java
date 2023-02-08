package collections.exceptions;

/**
 * Exception thrown when an operation is not valid.
 */
public class InvalidOperationException extends Exception {

    /**
     * Constructor
     */
    public InvalidOperationException() {
        super();
    }

    /**
     * Constructor with message
     *
     * @param message the message to be displayed
     */
    public InvalidOperationException(String message) {
        super(message);
    }

}