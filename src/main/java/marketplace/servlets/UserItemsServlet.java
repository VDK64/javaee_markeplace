package marketplace.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static marketplace.constants.ApplicationConstants.MY_ITEMS_PAGE;
import static marketplace.constants.ApplicationConstants.SHOW_ITEMS_PAGE;

/**
 * Servlet representing all items of certain user.
 */
public class UserItemsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(MY_ITEMS_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.logout();
        resp.sendRedirect(SHOW_ITEMS_PAGE);
    }

}