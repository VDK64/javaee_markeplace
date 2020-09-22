package marketplace.exception;

/**
 * Class with errors related to bids.
 */
public class BidErrorMessageConstants {
    public static final String WRONG_BIDDER = "Wrong bid logic: seller can not makes bid to his items.";
    public static final String WRONG_AMOUNT = "Amount can not be less than best offer + bid increment.";
    public static final String BID_CLOSED = "Trading in this lot has been stopped.";

}
