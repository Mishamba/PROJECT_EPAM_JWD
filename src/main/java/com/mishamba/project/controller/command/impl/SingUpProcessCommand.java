package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class SingUpProcessCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Properties singUpInfo = formSingUpInfo(request);
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("role");
        if (singUpInfo == null || !(userRole == null ||
                ((singUpInfo.get("role").equals("admin") ||
                        singUpInfo.get("role").equals("teacher")) &&
                        userRole.equals("admin")))) {
            logger.warn("someone is with role \"" + userRole +
                    "\" tries to create user with role \"" +
                    singUpInfo.get("role"));
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload index.jsp");
            }
        } else {

            boolean result;
            try {
                result = CustomServiceFactory.getInstance().getCustomService().
                        createUser(singUpInfo);
            } catch (ServiceException e) {
                logger.error("can't create user");
                result = false;
            }

            Properties info;
            try {
                info = CustomServiceFactory.getInstance().
                        getCustomService().getUserByEmail(singUpInfo.getProperty("email"));
            } catch (ServiceException e) {
                logger.error("can't get user info");
                request.setAttribute("info", "u singed up. now go and sing in");
                try {
                    request.getRequestDispatcher("info_page_with_parameter.jsp").
                            forward(request, response);
                } catch (ServletException | IOException servletException) {
                    logger.error("can't upload info page with answer");
                }

                return;
            }

            String pageToLoad;
            if (result) {
                if (userRole == null) {
                    session.setAttribute("role", singUpInfo.get("role"));
                    session.setAttribute("firstName", singUpInfo.get("firstName"));
                    session.setAttribute("lastName", singUpInfo.get("lastName"));
                    session.setAttribute("id", Integer.parseInt((String) info.get("id")));
                }
                pageToLoad = "index.jsp";
            } else {
                pageToLoad = "error.html";
            }

            try {
                request.getRequestDispatcher(pageToLoad).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload page");
            }
        }
    }

    private Properties formSingUpInfo(HttpServletRequest request) {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");
        String role = request.getParameter("role");
        if (role == null) {
            role = "student";
        }

        Properties singUpInfo;
        if (firstName != null || lastName != null || email != null ||
                password != null || birthday != null) {
            singUpInfo = new Properties();
            singUpInfo.setProperty("firstName", firstName);
            singUpInfo.setProperty("lastName", lastName);
            singUpInfo.setProperty("email", email);
            singUpInfo.setProperty("password", password);
            singUpInfo.setProperty("birthday", birthday);
            singUpInfo.setProperty("role", role);
        } else {
            singUpInfo = null;
        }

        return singUpInfo;
    }
}
