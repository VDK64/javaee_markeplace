package marketplace.services.impl;

import marketplace.domain.UserDto;
import marketplace.entities.User;
import marketplace.exception.UserErrorMessageConstants;
import marketplace.exception.UserException;
import marketplace.persistence.UserDao;
import marketplace.services.UserService;
import marketplace.types.Gender;
import marketplace.validation.UserValidator;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.*;

public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;
    @Inject
    private UserValidator userValidator;

    @Override
    public void saveUser(Map<String, String[]> parameterMap, UserDto userDto) throws SQLException, IOException {
        userValidator.validate(parameterMap, userDto);
        final User user = assembleUser(parameterMap);
        userDao.saveUser(user);
    }

    @Override
    public User getUser(String email) throws IOException, SQLException {
        final Optional<User> userByEmail = userDao.findByEmail(email);
        return userByEmail.orElseThrow(
                () -> new UserException(UserErrorMessageConstants.NOT_FOUND));
    }

    private User assembleUser(Map<String, String[]> parameterMap) {
        final User user = new User();
        user.setFirstName(parameterMap.get(FIRST_NAME)[0]);
        user.setLastName(parameterMap.get(LAST_NAME)[0]);
        user.setGender(Gender.valueOf(parameterMap.get(GENDER)[0]));
        user.setEmail(parameterMap.get(EMAIL)[0]);
        user.setPassword(parameterMap.get(CREDENTIALS)[0]);
        return user;
    }
}
