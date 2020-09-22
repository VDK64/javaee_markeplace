package marketplace.config.connection_pool.impl;

import marketplace.config.connection_pool.ConnectionPool;
import marketplace.config.connection_pool.ConnectionWrapper;

import java.sql.Connection;

/**
 * It's a connection wrapper implementation. It needs to override connection close logic.
 * This class implements {@link ConnectionWrapper} which implements
 * {@link java.io.Closeable} for this necessary.
 */
public class ConnectionWrapperImpl implements ConnectionWrapper {
    private final ConnectionPool connectionPool;
    private final Connection connection;

    public ConnectionWrapperImpl(ConnectionPool connectionPool, Connection connection) {
        this.connectionPool = connectionPool;
        this.connection = connection;
    }

    /**
     * Redefined method for release connection instead close.
     */
    @Override
    public void close() {
        connectionPool.releaseConnection(this);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
