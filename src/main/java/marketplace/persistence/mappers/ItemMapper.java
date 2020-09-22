package marketplace.persistence.mappers;

import marketplace.entities.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Mapper interface. Needs to map data from database oin POJO classes.
 */
public interface ItemMapper {

    /**
     * Map item from database on POJO class
     *
     * @param rs result set after sql query execution.
     * @return item.
     * @throws SQLException will be thrown if exception occur.
     */
    Item mapItem(ResultSet rs) throws SQLException;

    /**
     * Map items from database on list of POJO classes.
     *
     * @param rs result set after sql query execution.
     * @return list of items.
     * @throws SQLException will be thrown if exception occur.
     */
    List<Item> mapItems(ResultSet rs) throws SQLException;

}
