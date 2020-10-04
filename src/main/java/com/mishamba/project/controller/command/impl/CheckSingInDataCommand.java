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

public class CheckSingInDataCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("checking sing in data");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = null;
        String lastName = null;
        String role = null;
        String pageToLoad = "error.html";

        try {
            if (CustomServiceImpl.getInstance().checkSingInData(email, password)) {
                logger.info("user entered correct sing in data");
                Properties info = new Properties();
                logger.info("getting first name");
                info.setProperty("parameter", "first_name");
                info.setProperty("email", email);
                firstName = CustomServiceImpl.getInstance().
                        getUserParameter(info);
                logger.info("getting last name");
                info.setProperty("parameter", "last_name");
                lastName = CustomServiceImpl.getInstance().
                        getUserParameter(info);
                logger.info("getting role");
                info.setProperty("parameter", "role");
                role = CustomServiceImpl.getInstance().
                        getUserParameter(info);
                HttpSession session = request.getSession();
                session.setAttribute("firstName", firstName);
                session.setAttribute("lastName", lastName);
                session.setAttribute("role", role);
                pageToLoad = "main.jsp";
            }
        } catch (ServiceException e) {
            logger.error("can't check sing in data");
        }

        try {
            request.getRequestDispatcher(pageToLoad).forward(request, response);
            Properties userParameters = new Properties();
            userParameters.setProperty("firstName", firstName);
            userParameters.setProperty("lastName", lastName);
            userParameters.setProperty("role", role);
            request.setAttribute("user_info", CustomServiceImpl.getInstance().
                    getUserParameter(userParameters));
        } catch (ServletException | IOException | ServiceException e) {
            logger.error("can't upload error page");
        }
    }
}
