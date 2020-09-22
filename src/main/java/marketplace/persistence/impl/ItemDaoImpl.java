package marketplace.persistence.impl;

import marketplace.config.connection_pool.ConnectionPool;
import marketplace.config.connection_pool.ConnectionWrapper;
import marketplace.config.connection_pool.impl.BasicConnectionPool;
import marketplace.domain.ItemForUpdateDto;
import marketplace.entities.Item;
import marketplace.persistence.ItemDao;
import marketplace.persistence.mappers.ItemMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.NOBODY;
import static marketplace.queries.ItemQueriesConstants.*;

/**
 * Data access object layer to implements working with database.
 */
public class ItemDaoImpl implements ItemDao {

    @Inject
    private ItemMapper itemMapper;
    private final ConnectionPool connectionPool;

    public ItemDaoImpl() {
        connectionPool = BasicConnectionPool.getInstance();
    }

    /**
     * Save item in database.
     *
     * @param item        which need to save.
     * @param principalId who want to save item. (Owner of an item.)
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public void saveItem(ItemForUpdateDto item, long principalId)
            throws SQLException, IOException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
             final PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(INSERT_INTO_ITEM)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getSeller());
            preparedStatement.setFloat(4, item.getStartPrice());
            preparedStatement.setFloat(5, item.getBidInc());
            preparedStatement.setFloat(6, 0L);
            preparedStatement.setString(7, NOBODY);
            preparedStatement.setString(8, item.getStopDate());
            preparedStatement.setLong(9, item.getUserId());
            preparedStatement.execute();
        }
    }

    /**
     * Method for editing certain item in database.
     *
     * @param item        which need to edit.
     * @param principalId is an owner of item who want to edit it.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public void editItem(ItemForUpdateDto item, long principalId)
            throws SQLException, IOException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
             final PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(UPDATE_ITEM)) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getId());
            preparedStatement.execute();
        }
    }

    /**
     * Get all items from a database.
     *
     * @return list of items.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public List<Item> getAllItems() throws SQLException, IOException {
        try (ConnectionWrapper connection = connectionPool.getConnection();
             Statement statement = connection.getConnection().createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_FROM_ITEM);
            return itemMapper.mapItems(rs);
        }
    }

    /**
     * Method for receiving items of current user.
     *
     * @param email of a user (principal).
     * @return list of items from a database.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public List<Item> getCurrentUserItems(String email) throws SQLException, IOException {
        try (ConnectionWrapper connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(SELECT_FROM_ITEM_BY_USER_ID)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            return itemMapper.mapItems(rs);
        }
    }

    /**
     * Find item for it id in a database.
     *
     * @param itemId is the id of item.
     * @return item, wrapped in <code>Optional</code> class.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public Optional<Item> findItemById(Long itemId) throws SQLException, IOException {
        try (ConnectionWrapper connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(SELECT_FROM_ITEM_BY_ID)) {
            preparedStatement.setLong(1, itemId);
            ResultSet rs = preparedStatement.executeQuery();
            final Item item = itemMapper.mapItem(rs);
            if (item.getId() == 0)
                return Optional.empty();
            return Optional.of(item);
        }
    }

    /**
     * This method update offer and bedder info in item table in database.
     *
     * @param itemId    values of which need to update.
     * @param bestOffer is a new best offer.
     * @param bidder    who made a bid.
     * @throws SQLException will thrown when sql exception occurred.
     * @throws IOException  will thrown whe input/output exception occurred.
     */
    @Override
    public void updateOfferAndBidder(long itemId, float bestOffer, String bidder)
            throws SQLException, IOException {
        try (ConnectionWrapper connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(UPDATE_ITEM_AFTER_BID)) {
            preparedStatement.setFloat(1, bestOffer);
            preparedStatement.setString(2, bidder);
            preparedStatement.setLong(3, itemId);
            preparedStatement.execute();
        }
    }
}