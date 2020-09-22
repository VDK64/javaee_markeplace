package marketplace.validation;

import marketplace.entities.Item;
import marketplace.entities.User;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Interface of bid validator. Describes basic methods of bid validation.
 */
public interface BidValidator {
    /**
     * Method for validating request parameters.
     *
     * @param parameters from user request.
     */
    void validate(Map<String, String[]> parameters);

    /**
     * Checks bid parameters to make decision in adding new bid.
     *
     * @param user      which want to make bid.
     * @param amount    is a price of item.
     * @param item      is a cause of bid.
     * @param parseDate is a date in right format.
     */
    void validateParametersToBid(User user, float amount, Item item, Date parseDate) throws ParseException;

}
