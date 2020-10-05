package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomService;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class SingInProcessCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("checking sing in data");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String pageToLoad = "error.html";

        try {
            if (CustomServiceImpl.getInstance().
                    checkSingInData(email, password)) {
                logger.info("user entered correct sing in data");
                Properties info = CustomServiceImpl.getInstance().
                        getUserByEmail(email);
                HttpSession session = request.getSession();
                session.setAttribute("firstName", info.get("firstName"));
                session.setAttribute("lastName", info.get("lastName"));
                session.setAttribute("role", info.get("role"));
                pageToLoad = "index.jsp";
            }
        } catch (ServiceException e) {
            logger.error("can't check sing in data");
        }

        try {
            request.getRequestDispatcher(pageToLoad).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload error page");
        }
    }
}
