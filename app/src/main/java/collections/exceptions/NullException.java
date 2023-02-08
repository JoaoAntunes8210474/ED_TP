package collections.exceptions;

/**
 * Exception thrown when an element is null
 */
public class NullException extends Exception {

    /**
     * Creates a new instance of <code>NullException</code> with a detail message.
     *
     * @param message the detail message
     */
    public NullException(String message) {
        super("Erro " + message);
    }
}
