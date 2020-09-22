package marketplace.rest_resources;

import marketplace.domain.ResponseDto;
import marketplace.services.ItemService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Rest controller class which returns only items of current user.
 */
@Path("user-data")
public class UserItemsRestController {

    @Inject
    private ItemService service;

    /**
     * This method obtain client request to receive current user's items in JSON.
     *
     * @return ResponseDTO - data transfer object representing list of items.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{email}")
    public ResponseDto getRandomResponse(@PathParam("email") String email)
            throws SQLException, IOException {
        return service.getCurrentUserItems(email);
    }

}