package marketplace.exception;

/**
 * Extended from <code>RuntimeException</code> class to implement
 * exceptions related to connection pool.
 */
public class ConnectionPoolException extends RuntimeException {

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

}
