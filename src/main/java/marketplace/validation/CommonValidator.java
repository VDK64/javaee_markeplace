package marketplace.validation;

import marketplace.exception.ItemErrorMessageConstants;
import marketplace.exception.ItemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static marketplace.constants.ApplicationConstants.DATE_FORMAT_WITHOUT_TIME;

/**
 * Validator which consider main validation methods.
 */
public final class CommonValidator {

    public void validateTime(String stopTime) {
        try {
            LocalTime.parse(stopTime);
        } catch (DateTimeParseException e) {
            throw new ItemException(ItemErrorMessageConstants.WRONG_TIME_FORMAT);
        }
    }

    public void validateDate(String stopDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_WITHOUT_TIME);
        try {
            dateFormat.parse(stopDate);
        } catch (ParseException e) {
            throw new ItemException(ItemErrorMessageConstants.WRONG_DATE_FORMAT);
        }
    }

    public void validateFloat(String startPrice) throws NumberFormatException {
        try {
            Float.valueOf(startPrice);
        } catch (NumberFormatException e) {
            throw new ItemException(ItemErrorMessageConstants.WRONG_FLOAT_VALUE);
        }
    }

    public void validateDescription(String description) throws ItemException {
        if (description.length() > 200) {
            throw new ItemException(ItemErrorMessageConstants.LONG_DESCRIPTION);
        }
    }

    public void validateTitle(String title) throws ItemException {
        if (title.length() > 30) {
            throw new ItemException(ItemErrorMessageConstants.LONG_TITLE);
        }
    }

    public void validateLong(String id) throws NumberFormatException {
        try {
            Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ItemException(ItemErrorMessageConstants.WRONG_INTEGER_VALUE);
        }
    }

}
