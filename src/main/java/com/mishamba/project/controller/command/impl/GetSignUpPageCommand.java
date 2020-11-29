package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class sends Sign Up page for user
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetSignUpPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetSignUpPageCommand.class);
    private final String SIGN_UP_PAGE = "pages/sign_up.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(SIGN_UP_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload sign up page");
        }
    }
}
