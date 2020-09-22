package marketplace.servlets;

import marketplace.entities.User;
import marketplace.services.BidService;
import marketplace.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This a bid servlet - entry point for requests related to bids.
 */
public class BidServlet extends HttpServlet {

    @Inject
    private BidService bidService;
    @Inject
    private UserService userService;
    private final Logger logger = LogManager.getLogger(BidServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        final Map<String, String[]> parameters = req.getParameterMap();
        try {
            final User principal = userService.getUser(req.getRemoteUser());
            bidService.makeBid(principal, parameters);
            resp.sendRedirect(req.getContextPath());
        } catch (Exception exception) {
            logger.warn("Exception occur.", exception);
            throw new ServletException(exception);
        }
    }

}
