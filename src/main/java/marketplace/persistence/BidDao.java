package marketplace.persistence;

import marketplace.entities.User;

/**
 * Data access object interface with methods relate with bid.
 */
public interface BidDao {

    void doBid(String timeStamp, float amount, long userId, long itemId, User user, String bidder)
            throws Exception;

}
