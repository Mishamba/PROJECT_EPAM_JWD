package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class sends page for user with teacher role
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetCreateHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCreateHometaskPageCommand.class);
    private final String CREATE_HOMETASK_PAGE = "pages/create_hometask.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(CREATE_HOMETASK_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send create hometask page");
        }
    }
}
