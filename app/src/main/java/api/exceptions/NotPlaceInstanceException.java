package api.exceptions;


    
/**
 * Exception that is thrown when an element is not a place instance.
 */
public class NotPlaceInstanceException extends Exception {

    /**
     * Constructor without message.
     */
    public NotPlaceInstanceException() {
        super();
    }

    /**
     * Constructor without message.
     *
     * @param s message.
     */
    public NotPlaceInstanceException(String s) {
        super(s);
    }
}


