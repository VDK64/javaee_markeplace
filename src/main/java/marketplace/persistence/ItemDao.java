package marketplace.persistence;

import marketplace.domain.ItemForUpdateDto;
import marketplace.entities.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data access object interface with methods relate with item.
 */
public interface ItemDao {

    /**
     * Save item in database.
     *
     * @param item        which need to save.
     * @param principalId who want to save item. (Owner of an item.)
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    void saveItem(ItemForUpdateDto item, long principalId) throws SQLException, IOException;

    /**
     * Method for editing certain item in database.
     *
     * @param item        which need to edit.
     * @param principalId is an owner of item who want to edit it.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    void editItem(ItemForUpdateDto item, long principalId) throws SQLException, IOException;

    /**
     * Get all items from a database.
     *
     * @return list of items.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    List<Item> getAllItems() throws SQLException, IOException;

    /**
     * Method for receiving items of current user.
     *
     * @param email of a user (principal).
     * @return list of items from a database.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    List<Item> getCurrentUserItems(String email) throws SQLException, IOException;

    /**
     * Find item for it id in a database.
     *
     * @param itemId is the id of item.
     * @return item, wrapped in <code>Optional</code> class.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    Optional<Item> findItemById(Long itemId) throws SQLException, IOException;

    /**
     * This method update offer and bedder info in item table in database.
     *
     * @param itemId    values of which need to update.
     * @param bestOffer is a new best offer.
     * @param bidder    who made a bid.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    void updateOfferAndBidder(long itemId, float bestOffer, String bidder) throws SQLException, IOException;

}
