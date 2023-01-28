package api.exceptions;


/**
 * Exception that is thrown when a {@link Headquarter headquarter} already exists in a company graph.
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
