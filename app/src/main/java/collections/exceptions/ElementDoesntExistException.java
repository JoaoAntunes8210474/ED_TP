package collections.exceptions;


/**
 * Exception for when an element doesn't exist in a collection
 */
public class ElementDoesntExistException extends Exception {

    /**
     * Constructor
     */
    public ElementDoesntExistException() {
        super();
    }

    /**
     * Constructor with exception message
     * @param message Message
     */
    public ElementDoesntExistException(String message) {
        super(message);
    }

}
