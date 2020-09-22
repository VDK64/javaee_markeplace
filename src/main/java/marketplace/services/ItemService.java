package marketplace.services;

import marketplace.domain.ResponseDto;
import marketplace.entities.Item;
import marketplace.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Service which consists of item business logic.
 */
public interface ItemService {

    /**
     * Returns response in JSON. Response consists of list of items.
     *
     * @return ResponseDTO is a representation of list of items.
     * @throws SQLException if occurred.
     */
    ResponseDto getAllItems() throws SQLException, IOException;

    /**
     * This method returns only current user's items data.
     *
     * @param email of user.
     * @return ResponseDTO is a representation of list of items.
     * @throws SQLException if occurred.
     * @throws IOException  if occurred.
     */
    ResponseDto getCurrentUserItems(String email) throws SQLException, IOException;

    /**
     * Find item by it's id.
     *
     * @param itemId is id of item which need to find.
     * @return found item.
     * @throws SQLException if occurred.
     */
    Item getItem(String itemId) throws SQLException, IOException;

    /**
     * Edit item method.
     *
     * @param requestParameters item parameters received on server from user.
     * @param user              who send request to edit item.
     */
    void editItem(Map<String, String[]> requestParameters, User user) throws SQLException, IOException;

    String getDate(Item item);

    String getTime(Item item);
}
