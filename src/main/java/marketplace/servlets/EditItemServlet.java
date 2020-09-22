package marketplace.servlets;

import marketplace.entities.Item;
import marketplace.entities.User;
import marketplace.services.ItemService;
import marketplace.services.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.*;

/**
 * Servlet which routing edit item requests.
 */
public class EditItemServlet extends HttpServlet {

    @Inject
    private ItemService itemService;
    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String itemId = req.getParameter(ITEM_ID);
        try {
            final Item item = itemService.getItem(itemId);
            req.setAttribute(ITEM, item);
            req.setAttribute(DISABLED, EMPTY_STRING);
            if (item != null) {
                req.setAttribute(DISABLED, DISABLED);
                req.setAttribute(STOP_DATE_CAMEL_CASE, itemService.getDate(item));
                req.setAttribute(STOP_TIME_CAMEL_CASE, itemService.getTime(item));
            }
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
        getServletContext().getRequestDispatcher(EDIT_ITEM_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final Map<String, String[]> requestParameters = req.getParameterMap();
        try {
            final User principal = userService.getUser(req.getRemoteUser());
            itemService.editItem(requestParameters, principal);
        } catch (Exception exception) {
            throw new ServletException(exception);
        }
        getServletContext().getRequestDispatcher(ROOT_PATH).forward(req, resp);
    }

}
