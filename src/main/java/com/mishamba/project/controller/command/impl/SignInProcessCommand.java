package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class process sign in data and set user to session
 *
 * @version 1.0
 * @author Mishamba
 */

public class SignInProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SignUpProcessCommand.class);
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";
    private final String ERROR_PAGE = "pages/error.html";
    private final String INDEX_PAGE = "index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String pageToLoad = ERROR_PAGE;

        try {
            if (ServiceFactory.getInstance().getUserService().
                    checkSignInData(email, password)) {
                User user = ServiceFactory.getInstance().
                        getUserService().getUserByEmail(email).get();
                request.getSession().setAttribute("role", user.getRole());
                request.getSession().setAttribute("id", user.getId());
                pageToLoad = INDEX_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't check sign in data");
        }

        try {
            request.getRequestDispatcher(pageToLoad).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
