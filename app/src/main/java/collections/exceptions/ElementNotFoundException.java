package collections.exceptions;

/**
 * Exception thrown when an element is not found in a collection.
 */
public class ElementNotFoundException extends RuntimeException {
    /**
     * Constructor with exception message
     * @param message Message
     */
    public ElementNotFoundException(String message) {
        super("Erro " + message);
    }
}
