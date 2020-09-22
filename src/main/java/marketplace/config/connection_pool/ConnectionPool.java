package marketplace.config.connection_pool;

/**
 * Base interface of connection pool with basic methods.
 */
public interface ConnectionPool {

    ConnectionWrapper getConnection();

    void releaseConnection(ConnectionWrapper connection);

    void closeConnections();

}
