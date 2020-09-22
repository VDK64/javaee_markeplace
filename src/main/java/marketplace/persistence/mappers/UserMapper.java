package marketplace.persistence.mappers;

import marketplace.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper interface. Needs to map data from database oin POJO classes.
 */
public interface UserMapper {

    /**
     * Map item from database on POJO class.
     *
     * @param rs result set after sql query execution.
     * @return user.
     * @throws SQLException will be thrown if exception occur.
     */
    User mapUser(ResultSet rs) throws SQLException;

}
