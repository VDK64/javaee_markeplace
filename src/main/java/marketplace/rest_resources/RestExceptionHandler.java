package marketplace.rest_resources;

import marketplace.domain.ResponseDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception handler to handle exception from rest controllers.
 */
@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {
    /**
     * This method needs to response data in JSON when exceptions occurs.
     *
     * @param e exception
     * @return response in JSON.
     */
    @Override
    public Response toResponse(Exception e) {
        return Response.accepted(new ResponseDto(null, true)).build();
    }

}

