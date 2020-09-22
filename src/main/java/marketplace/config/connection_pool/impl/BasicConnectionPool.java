package marketplace.config.connection_pool.impl;

import marketplace.config.PropertyReader;
import marketplace.config.connection_pool.ConnectionPool;
import marketplace.config.connection_pool.ConnectionWrapper;
import marketplace.exception.ConnectionPoolErrorMessageConstants;
import marketplace.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * This is a class which implements connection pool.
 * Value of connections is configurable and stores in application.properties file.
 *
 * Loading of a class performing via eager singleton. It's needs because there is a two
 * implementation of dependency injection in this application and connection pool must be
 * loading once both of this implementations.
 */
public class BasicConnectionPool implements ConnectionPool {
    private static final ConnectionPool INSTANCE;
    private final Properties properties;
    private final String URL;
    private final List<ConnectionWrapper> connectionPool;
    private final List<ConnectionWrapper> usedConnections;
    private static final int MAX_POOL_SIZE;
    private static final String POOL_SIZE_PROPERTY = "pool.size";
    private static final String USER = "user";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String SSL = "ssl";
    private static final String FALSE = "false";

    /*
     This static block need to load property of connection pool nad to eagerly load instance of this class.
     */
    static {
        MAX_POOL_SIZE = Integer.parseInt(PropertyReader.PROPERTIES.getProperty(POOL_SIZE_PROPERTY));
        INSTANCE = new BasicConnectionPool();
    }

    /**
     * Basic constructor for class instantiate and set all necessary properties.
     * Also, this constructor create connection pool.
     */
    private BasicConnectionPool() {
        this.URL = PropertyReader.PROPERTIES.getProperty("url");
        properties = new Properties();
        assemblePersistenceProperty();
        connectionPool = createConnectionPool();
        this.usedConnections = Collections.synchronizedList(new ArrayList<>(MAX_POOL_SIZE));
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnections));
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    /**
     * Gives connection from own connection pool if exists.
     * When a connection was found - it is removed from the pool of connections
     * and added to the list of connections in use.
     * In other case throw {@link ConnectionPoolException}.
     *
     * @return {@link ConnectionWrapper}
     */
    @Override
    public ConnectionWrapper getConnection() {
        if (connectionPool.size() == 0)
            throw new ConnectionPoolException(ConnectionPoolErrorMessageConstants.NO_FREE_CONNECTION);
        final ConnectionWrapper connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    /**
     * This method release connection by removing from list of connections
     * in use and adding connection to connection pool.
     *
     * @param connection which needs to release.
     */
    @Override
    public void releaseConnection(ConnectionWrapper connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    /**
     * Method for closing connections. Using when JVM is shutdown.
     */
    @Override
    public void closeConnections() {
        closeAllConnections(connectionPool);
        closeAllConnections(usedConnections);
    }

    private void closeAllConnections(List<ConnectionWrapper> connectionPool) {
        connectionPool.forEach(connection -> {
            try {
                connection.getConnection().close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void assemblePersistenceProperty() {
        properties.setProperty(USER, PropertyReader.PROPERTIES.getProperty(USER_NAME));
        properties.setProperty(PASSWORD, PropertyReader.PROPERTIES.getProperty(PASSWORD));
        properties.setProperty(SSL, FALSE);
    }

    private List<ConnectionWrapper> createConnectionPool() {
        final List<ConnectionWrapper> pool
                = Collections.synchronizedList(new ArrayList<>(MAX_POOL_SIZE));
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return pool;
    }

    private ConnectionWrapper createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ConnectionWrapperImpl(this, connection);
    }

    public static void load() {
    }

}