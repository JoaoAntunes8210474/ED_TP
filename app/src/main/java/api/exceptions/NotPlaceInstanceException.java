package api.exceptions;


    
/**
 * Throws exception when element in question must be instanced of {@link IPlace place} but isn't.
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


