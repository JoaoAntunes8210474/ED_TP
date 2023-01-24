
package collections.exceptions;

public class NullException extends Exception {

    public NullException(String message) {
        super("Erro " + message);
    }
}
