package marketplace.persistence;

import marketplace.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data access object interface with methods relate with user.
 */
public interface UserDao {
    /**
     * Create and save user in database.
     *
     * @param user which need to save in database.
     * @throws SQLException will be thrown if occur.
     */
    void saveUser(User user) throws SQLException, IOException;

    /**
     * Find user by it's email.
     *
     * @param email of the user.
     * @return user wrapped in <code>Optional</code> class.
     * @throws SQLException will be thrown if occur.
     * @throws IOException  will be thrown if occur.
     */
    Optional<User> findByEmail(String email) throws SQLException, IOException;

    /**
     * Count user by certain email.
     *
     * @param email by which need to find.
     * @return count of id of users.
     */
    long countUserByEmail(String email) throws IOException, SQLException;

}
