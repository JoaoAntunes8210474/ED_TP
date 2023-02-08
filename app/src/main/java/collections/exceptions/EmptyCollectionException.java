package collections.exceptions;

/**
 * Exception thrown when a collection is empty.
 */
public class EmptyCollectionException extends Exception {

    /**
     * Constructor with an error message
     *
     * @param message the error message
     */
    public EmptyCollectionException(String message) {
        super("Erro " + message);
    }
}
