package marketplace.config.connection_pool;

import java.io.Closeable;
import java.sql.Connection;

/**
 * Interface of <code>ConnectionWrapper</code> with one basic method.
 */
public interface ConnectionWrapper extends Closeable {

    Connection getConnection();

}
