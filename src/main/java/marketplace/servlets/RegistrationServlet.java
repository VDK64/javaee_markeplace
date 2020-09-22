package marketplace.servlets;

import marketplace.domain.UserDto;
import marketplace.exception.UserAlreadyExistsException;
import marketplace.services.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static marketplace.constants.ApplicationConstants.LOGIN_PAGE;
import static marketplace.constants.ApplicationConstants.REGISTRATION_PAGE;

public class RegistrationServlet extends HttpServlet {

    @Inject
    private UserService userService;
    private static final String SUCCESS_PATH_PARAMETER = "?success=1";
    private static final String EXISTS_PARAMETER = "?exists=1";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Map<String, String[]> parameterMap = req.getParameterMap();
        final UserDto userDto = new UserDto();
        try {
            userService.saveUser(parameterMap, userDto);
            resp.sendRedirect(LOGIN_PAGE + SUCCESS_PATH_PARAMETER);
        } catch (UserAlreadyExistsException exception) {
            req.setAttribute("userDto", userDto);
            getServletContext().getRequestDispatcher(REGISTRATION_PAGE + EXISTS_PARAMETER).forward(req, resp);
        }
        catch (Exception exception) {
            throw new ServletException(exception);
        }
    }

}
