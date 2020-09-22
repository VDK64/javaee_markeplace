package marketplace.exception;

/**
 * Extended from <code>RuntimeException</code> class to implement
 * exceptions related to items.
 */
public class ItemException extends RuntimeException {

    public ItemException(String message) {
        super(message);
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }

}
