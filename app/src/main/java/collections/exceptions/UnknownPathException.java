package collections.exceptions;

/**
 * An exception that is thrown when a path is not found.
 */
public class UnknownPathException extends Exception {

    /**
     * Creates a new instance of <code>UnknownPathException</code> without detail
     */
    public UnknownPathException() {
        super();
    }

    /**
     * Constructs an instance of <code>UnknownPathException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public UnknownPathException(String message) {
        super(message);
    }
}
