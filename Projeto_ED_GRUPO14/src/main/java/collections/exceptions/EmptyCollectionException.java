
package collections.exceptions;


public class EmptyCollectionException extends Exception {

    public EmptyCollectionException(String message) {
        super("Erro " + message);
    }
}
