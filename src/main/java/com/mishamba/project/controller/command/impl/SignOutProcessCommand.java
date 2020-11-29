package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class drop session info
 *
 * @version 1.0
 * @author Mishamba
 */

public class SignOutProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SignOutProcessCommand.class);
    private final String INDEX_PAGE = "pages/index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        try {
            request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload index.jsp");
        }
    }
}
