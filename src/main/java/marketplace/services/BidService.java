package marketplace.services;

import marketplace.entities.User;

import java.util.Map;

/**
 * Service which consists of bid business logic.
 */
public interface BidService {

    /**
     * Make bid and persist in database.
     *
     * @param user       which doing bid.
     * @param parameters of user's request.
     */
    void makeBid(User user, Map<String, String[]> parameters) throws Exception;

}
