package api.exceptions;


/**
 * Exception that is thrown when an element already exists.
 */
public class ElementAlreadyExistsException extends Exception{

    /**
     * Constructor without message.
     */
    public ElementAlreadyExistsException() {
        super();
    }

    /**
     * Constructor without message
     * @param s message.
     */
    public ElementAlreadyExistsException(String s) {
        super(s);
    }

}
