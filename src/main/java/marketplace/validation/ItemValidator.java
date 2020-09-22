package marketplace.validation;

import java.util.Map;

/**
 * Interface of item validator. Describes basic methods of item validation.
 */
public interface ItemValidator {
    /**
     * Method for validating request parameters.
     *
     * @param parameters from user request.
     */
    void validate(Map<String, String[]> parameters);

}
