package marketplace.validation.impl;

import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.exception.BidErrorMessageConstants;
import marketplace.exception.BidException;
import marketplace.validation.BidValidator;
import marketplace.validation.CommonValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.AMOUNT;
import static marketplace.constants.ApplicationConstants.ITEM_ID;

/**
 * Validates Bids conditions.
 */
public class BidValidatorImpl implements BidValidator {

    @Inject
    private CommonValidator commonValidator;

    /**
     * Method for validating request parameters.
     *
     * @param parameters from user request.
     */
    @Override
    public void validate(Map<String, String[]> parameters) {
        final String itemId = parameters.get(ITEM_ID)[0];
        final String amount = parameters.get(AMOUNT)[0];
        commonValidator.validateLong(itemId);
        commonValidator.validateFloat(amount);
    }

    /**
     * Checks bid parameters to make decision in adding new bid.
     *
     * @param user      which want to make bid.
     * @param amount    is a price of item.
     * @param item      is a cause of bid.
     * @param parseDate is a date in right format.
     */
    @Override
    public void validateParametersToBid(User user, float amount, Item item, Date parseDate) {
        if (user.getId() == item.getUserId()) {
            throw new BidException(BidErrorMessageConstants.WRONG_BIDDER);
        }
        final float minimumAmountToBid = BigDecimal.valueOf(item.getBestOffer() + item.getBidInc())
                .setScale(2, RoundingMode.HALF_EVEN).floatValue();
        if (amount < minimumAmountToBid) {
            throw new BidException(BidErrorMessageConstants.WRONG_AMOUNT);
        }
        validateItemStatus(parseDate);
    }

    public void validateItemStatus(Date parseDate) {
        final Date nowDate = new Date(System.currentTimeMillis() + 10000);
        if (parseDate.before(nowDate)) {
            throw new BidException(BidErrorMessageConstants.BID_CLOSED);
        }
    }

}
