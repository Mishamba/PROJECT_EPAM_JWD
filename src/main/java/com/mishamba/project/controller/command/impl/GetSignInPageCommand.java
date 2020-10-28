package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetSignInPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetSignInPageCommand.class);
    private final String SIGN_IN_PAGE = "pages/sign_in.html";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(SIGN_IN_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload sign in page");
        }
    }
}
