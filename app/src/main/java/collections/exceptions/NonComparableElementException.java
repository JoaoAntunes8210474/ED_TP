package collections.exceptions;

/**
 * Exception thrown when an element is not comparable
 */
public class NonComparableElementException extends Exception {

    /**
     * Creates a new instance of <code>NonComparableElementException</code> with a detail message.
     *
     * @param message the detail message
     */
    public NonComparableElementException(String message) {
        super("Erro " + message);
    }
}
