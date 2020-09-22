package marketplace.validation.impl;

import marketplace.validation.CommonValidator;
import marketplace.validation.ItemValidator;

import javax.inject.Inject;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Validates item conditions.
 */
public class ItemValidatorImpl implements ItemValidator {

    @Inject
    private CommonValidator commonValidator;

    /**
     * Method for validating request parameters.
     *
     * @param parameters from user request.
     */
    @Override
    public void validate(Map<String, String[]> parameters) {
        if (parameters.containsKey(ITEM_ID)) {
            final String id = parameters.get(ITEM_ID)[0];
            commonValidator.validateLong(id);
        }
        final String title = parameters.get(TITLE)[0];
        commonValidator.validateTitle(title);
        final String description = parameters.get(DESCRIPTION)[0];
        commonValidator.validateDescription(description);
        if (parameters.containsKey(START_PRICE_CAMEL_CASE)) {
            final String startPrice = parameters.get(START_PRICE_CAMEL_CASE)[0];
            commonValidator.validateFloat(startPrice);
        }
        if (parameters.containsKey(BID_INCREMENT)) {
            final String bidIncrement = parameters.get(BID_INCREMENT)[0];
            commonValidator.validateFloat(bidIncrement);
        }
        if (parameters.containsKey(STOP_DATE_CAMEL_CASE)) {
            final String stopDate = parameters.get(STOP_DATE_CAMEL_CASE)[0];
            commonValidator.validateDate(stopDate);
        }
        if (parameters.containsKey(STOP_TIME_CAMEL_CASE)) {
            final String stopTime = parameters.get(STOP_TIME_CAMEL_CASE)[0];
            commonValidator.validateTime(stopTime);
        }
    }

}
