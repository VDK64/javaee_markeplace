package marketplace.validation.impl;

import marketplace.domain.UserDto;
import marketplace.exception.UserAlreadyExistsException;
import marketplace.exception.UserErrorMessageConstants;
import marketplace.exception.UserException;
import marketplace.persistence.UserDao;
import marketplace.types.Gender;
import marketplace.validation.UserValidator;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.*;
import static marketplace.exception.UserErrorMessageConstants.*;

public class UserValidatorImpl implements UserValidator {

    @Inject
    private UserDao userDAO;
    private static final String CYRILLIC_REGEX = "[А-Яа-я]";
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_!@#$%^&*+=])(?=\\S+$).{4,15}$";
    private static final String CHECKED = "checked";

    @Override
    public void validate(Map<String, String[]> parameterMap, UserDto userDTO)
            throws IOException, SQLException {
        if (parameterMap.containsKey(FIRST_NAME)) {
            final String firstName = parameterMap.get(FIRST_NAME)[0];
            validateFirstName(firstName);
            userDTO.setFirstName(firstName);
        } else {
            throw new UserException(UserErrorMessageConstants.EMPTY_FIRST_NAME);
        }
        if (parameterMap.containsKey(LAST_NAME)) {
            final String lastName = parameterMap.get(LAST_NAME)[0];
            validateLastName(lastName);
            userDTO.setLastName(lastName);
        } else {
            throw new UserException(UserErrorMessageConstants.EMPTY_LAST_NAME);
        }
        if (parameterMap.containsKey(GENDER)) {
            final String gender = parameterMap.get(GENDER)[0];
            validateGender(gender);
            setEmailToDto(userDTO, gender);
        } else {
            throw new UserException(UserErrorMessageConstants.EMPTY_GENDER);
        }
        if (parameterMap.containsKey(EMAIL)) {
            final String email = parameterMap.get(EMAIL)[0];
            validateEmail(email);
            userDTO.setEmail(email);
        } else {
            throw new UserException(UserErrorMessageConstants.EMPTY_EMAIL);
        }
        if (parameterMap.containsKey(CREDENTIALS) && parameterMap.containsKey(CONFIRM_CREDENTIALS)) {
            final String credentials = parameterMap.get(CREDENTIALS)[0];
            final String confirmCredentials = parameterMap.get(CONFIRM_CREDENTIALS)[0];
            validateCredentials(credentials);
            compareCredentials(credentials, confirmCredentials);
        } else {
            throw new UserException(EMPTY_CREDENTIALS);
        }
    }

    private void setEmailToDto(UserDto userDTO, String gender) {
        if (Gender.valueOf(gender).equals(Gender.MALE)) {
            userDTO.setMaleGenderChecked(CHECKED);
        } else {
            userDTO.setFemaleGenderChecked(CHECKED);
        }
    }

    private void compareCredentials(String credentials, String confirmCredentials) {
        if (!credentials.equals(confirmCredentials)) {
            throw new UserException(DIFFERENT_PASSWORDS);
        }
    }

    private void validateCredentials(String credentials) {
        if (!credentials.matches(PASSWORD_REGEX)) {
            throw new UserException(INVALID_PASSWORD);
        }
    }

    private void validateGender(String stringGender) {
        try {
            Gender.valueOf(stringGender);
        } catch (IllegalArgumentException exception) {
            throw new UserException(INVALID_GENDER);
        }
    }

    private void validateEmail(String email) throws IOException, SQLException {
        if (!email.matches(EMAIL_REGEX)) {
            throw new UserException(INVALID_EMAIL);
        }
        final long count = userDAO.countUserByEmail(email);
        if (count > 0) {
            throw new UserAlreadyExistsException(ALREADY_EXISTS);
        }
    }

    private void validateLastName(String lastName) {
        if (lastName.matches(CYRILLIC_REGEX)) {
            throw new UserException(CYRILLIC_SYMBOLS_IN_LAST_NAME);
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName.matches(CYRILLIC_REGEX)) {
            throw new UserException(CYRILLIC_SYMBOLS_IN_FIRST_NAME);
        }
    }

}
