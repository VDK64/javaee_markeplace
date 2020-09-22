package marketplace.exception;

/**
 * Extended from <code>RuntimeException</code> class to implement
 * exceptions related to bid.
 */
public class BidException extends RuntimeException {

    public BidException(String message) {
        super(message);
    }

    public BidException(String message, Throwable cause) {
        super(message, cause);
    }

}
