package marketplace.rest_resources;

import marketplace.domain.ResponseDto;
import marketplace.services.ItemService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Rest controller class which returns all items.
 */
@Path("all-data")
public class ItemsRestController {

    @Inject
    private ItemService service;

    /**
     * This method obtain client request to receive all items data in JSON.
     *
     * @return ResponseDTO - data transfer object representing list of items.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseDto getRandomResponse() throws SQLException, IOException {
        return service.getAllItems();
    }

}
