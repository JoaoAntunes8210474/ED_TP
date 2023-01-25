
package collections.exceptions;



public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super("Erro " + message);
    }
}
