package marketplace.persistence.impl;

import marketplace.config.connection_pool.ConnectionPool;
import marketplace.config.connection_pool.ConnectionWrapper;
import marketplace.config.connection_pool.impl.BasicConnectionPool;
import marketplace.entities.User;
import marketplace.persistence.UserDao;
import marketplace.persistence.mappers.UserMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static marketplace.queries.UserQueriesConstants.*;

/**
 * Data access object layer to implements working with database.
 */
public class UserDaoImpl implements UserDao {

    @Inject
    private UserMapper userMapper;
    private final ConnectionPool connectionPool;

    public UserDaoImpl() {
        connectionPool = BasicConnectionPool.getInstance();
    }

    /**
     * Create and save user in database.
     *
     * @param user which need to save in database.
     * @throws SQLException will be thrown if occur.
     */
    @Override
    public void saveUser(User user) throws SQLException, IOException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
             final PreparedStatement preparedStatementUser
                     = connection.getConnection().prepareStatement(INSERT_USER);
             final PreparedStatement preparedStatementRoleUser
                     = connection.getConnection().prepareStatement(INSERT_ROLE_USER)) {
            preparedStatementUser.setString(1, user.getFirstName());
            preparedStatementUser.setString(2, user.getLastName());
            preparedStatementUser.setString(3, user.getEmail());
            preparedStatementUser.setString(4, user.getPassword());
            preparedStatementUser.setString(5, String.valueOf(user.getGender()));
            preparedStatementUser.execute();
            preparedStatementRoleUser.setString(1, user.getEmail());
            preparedStatementRoleUser.setLong(2, 1L);
            preparedStatementRoleUser.execute();
        }
    }

    /**
     * Find user by it's email.
     *
     * @param email of the user.
     * @return user wrapped in <code>Optional</code> class.
     * @throws SQLException will be thrown if occur.
     * @throws IOException  will be thrown if occur.
     */
    @Override
    public Optional<User> findByEmail(String email) throws SQLException, IOException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
            final PreparedStatement preparedStatement = connection.getConnection()
                    .prepareStatement(SELECT_FROM_USER_EMAIL)) {
            preparedStatement.setString(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final User user = userMapper.mapUser(resultSet);
            if (user.getId() == 0)
                return Optional.empty();
            return Optional.of(user);
        }
    }

    /**
     * Count user by certain email.
     *
     * @param email by which need to find.
     * @return count of id of users.
     */
    @Override
    public long countUserByEmail(String email) throws IOException, SQLException {
        try (final ConnectionWrapper connection = connectionPool.getConnection();
             final PreparedStatement preparedStatement = connection.getConnection()
                     .prepareStatement(COUNT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            long count = 0;
            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            return count;
        }
    }

}