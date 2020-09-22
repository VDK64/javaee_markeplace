package marketplace.persistence.impl;

import marketplace.config.connection_pool.ConnectionPool;
import marketplace.config.connection_pool.ConnectionWrapper;
import marketplace.config.connection_pool.impl.BasicConnectionPool;
import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.ItemErrorMessageConstants;
import marketplace.exception.ItemException;
import marketplace.persistence.BidDao;
import marketplace.persistence.ItemDao;
import marketplace.persistence.mappers.ItemMapper;
import marketplace.validation.BidValidator;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static marketplace.constants.ApplicationConstants.DATE_FORMAT;
import static marketplace.queries.BidQueriesConstants.INSERT_INTO_BID;
import static marketplace.queries.ItemQueriesConstants.SELECT_FROM_ITEM_BY_ID;

/**
 * Data access object layer to implements working with database.
 */
public class BidDaoImpl implements BidDao {

    @Inject
    private BidValidator validator;
    @Inject
    private ItemDao itemDAO;
    @Inject
    private ItemMapper itemMapper;
    /**
     * Needs to parse strings from database in Date in certain format.
     */
    private final SimpleDateFormat dateFormat;
    private final ConnectionPool connectionPool;

    public BidDaoImpl() {
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
        connectionPool = BasicConnectionPool.getInstance();
    }

    /**
     * Read certain item from database, then validate parameters.
     * If all parameters satisfies conditions, then this method write new
     * bid in database and update item values.
     *
     * @param timeStamp of bid.
     * @param amount    it is a price for item.
     * @param userId    id of bidder.
     * @param itemId    id of item.
     * @param user      which want to make a bid.
     * @param bidder    is a string with prefix. Needs to view in item table.
     * @throws IOException    will thrown if necessary.
     * @throws SQLException   will thrown if necessary.
     * @throws ParseException will thrown if necessary.
     */
    @Override
    public void doBid(String timeStamp, float amount, long userId, long itemId, User user, String bidder)
            throws IOException, SQLException, ParseException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
             final PreparedStatement bidPreparedStatement = connection.getConnection()
                     .prepareStatement(INSERT_INTO_BID);
             final PreparedStatement itemStatement = connection.getConnection()
                     .prepareStatement(SELECT_FROM_ITEM_BY_ID)) {
            connection.getConnection().setAutoCommit(false);
            final Item item = getItem(itemId, itemStatement);
            validateBeforeBid(amount, user, item);
            bidPreparedStatement.setFloat(1, amount);
            bidPreparedStatement.setString(2, timeStamp);
            bidPreparedStatement.setLong(3, userId);
            bidPreparedStatement.setLong(4, itemId);
            bidPreparedStatement.execute();
            itemDAO.updateOfferAndBidder(itemId, amount, bidder);
            connection.getConnection().commit();
            connection.getConnection().setAutoCommit(true);
        }
    }

    /**
     * Validating method for validate parameters before write new bid in database.
     *
     * @param amount it is a price of item.
     * @param user   which want to make a bid.
     * @param item   it's a cause of bid.
     * @throws ParseException will thrown when parse exception occurred.
     */
    private void validateBeforeBid(float amount, User user, Item item) throws ParseException {
        final Date parseDate = dateFormat.parse(item.getStopDate());
        validator.validateParametersToBid(user, amount, item, parseDate);

    }

    /**
     * Perform get item from database query.
     *
     * @param itemId        id of item.
     * @param itemStatement is a statement which need to execute to receive item from database.
     * @return item.
     * @throws SQLException will thrown if necessary.
     */
    private Item getItem(long itemId, PreparedStatement itemStatement) throws SQLException {
        itemStatement.setFloat(1, itemId);
        final ResultSet itemResultSet = itemStatement.executeQuery();
        final Item item = itemMapper.mapItem(itemResultSet);
        if (item.getId() == 0)
            throw new ItemException(ItemErrorMessageConstants.WRONG_ITEM);
        return item;
    }

}
