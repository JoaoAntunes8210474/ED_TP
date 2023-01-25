
package collections.exceptions;

public class NonComparableElementException extends Exception {

    public NonComparableElementException(String message) {
        super("Erro " + message);
    }
}
