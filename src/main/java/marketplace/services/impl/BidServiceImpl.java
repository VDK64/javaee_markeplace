package marketplace.services.impl;

import marketplace.entities.User;
import marketplace.persistence.BidDao;
import marketplace.services.BidService;
import marketplace.validation.BidValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.AMOUNT;
import static marketplace.constants.ApplicationConstants.ITEM_ID;

/**
 * Implementation of {@link BidService}.
 */
public class BidServiceImpl implements BidService {

    @Inject
    private BidDao bidDao;
    @Inject
    private BidValidator validator;
    private final SimpleDateFormat dateFormat;

    public BidServiceImpl() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    /**
     * Make bid and persist in database.
     *
     * @param user       which doing bid.
     * @param parameters of user's request.
     */
    @Override
    public void makeBid(User user, Map<String, String[]> parameters) throws Exception {
        validator.validate(parameters);
        final long itemId = Long.parseLong(parameters.get(ITEM_ID)[0]);
        final float amount = new BigDecimal(parameters.get(AMOUNT)[0])
                .setScale(2, RoundingMode.CEILING).floatValue();
        final String nowDateString = dateFormat
                .format(new Date(System.currentTimeMillis() + 10000));
        final String bidder = createBidderWithGenderPrefix(user);
        bidDao.doBid(nowDateString, amount, user.getId(), itemId, user, bidder);
    }

    private String createBidderWithGenderPrefix(User user) {
        final String prefix = user.getGender().getPrefix();
        return prefix + user.getLastName();
    }

}