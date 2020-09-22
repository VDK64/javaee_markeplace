package marketplace.persistence.mappers.impl;

import marketplace.entities.Role;
import marketplace.entities.User;
import marketplace.persistence.mappers.ItemMapper;
import marketplace.persistence.mappers.UserMapper;
import marketplace.types.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Implementation of {@link ItemMapper} interface.
 * Needs to map entities from database on POJO.
 */
public class UserMapperImpl implements UserMapper {
    /**
     * Map item from database on POJO class.
     *
     * @param rs result set after sql query execution.
     * @return user.
     * @throws SQLException will be thrown if exception occur.
     */
    @Override
    public User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getLong(ID));
            user.setFirstName(rs.getString(FIRST_NAME));
            user.setLastName(rs.getString(LAST_NAME));
            user.setEmail(rs.getString(EMAIL));
            user.setPassword(rs.getString(PASSWORD));
            user.setGender(Gender.valueOf(rs.getString(GENDER)));
            user.getAuthorities().add(Role.valueOf(rs.getString(ROLE)));
        }
        return user;
    }

}
