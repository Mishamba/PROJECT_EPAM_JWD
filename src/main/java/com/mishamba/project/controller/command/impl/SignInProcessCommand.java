package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class SignInProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SignUpProcessCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("checking sign in data");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String pageToLoad = "error.html";

        try {
            if (ServiceFactory.getInstance().getCustomService().
                    checkSignInData(email, password)) {
                logger.info("user entered correct sign in data");
                Properties info = ServiceFactory.getInstance().
                        getCustomService().getUserByEmail(email);
                HttpSession session = request.getSession();
                session.setAttribute("firstName", info.get("firstName"));
                session.setAttribute("lastName", info.get("lastName"));
                session.setAttribute("role", info.get("role"));
                session.setAttribute("id", Integer.parseInt((String) info.get("id")));
                pageToLoad = "index.jsp";
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
