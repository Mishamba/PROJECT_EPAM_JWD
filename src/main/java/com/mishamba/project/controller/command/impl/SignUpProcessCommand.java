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

public class SignUpProcessCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Properties signUpInfo = formSignUpInfo(request);
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("role");
        if (signUpInfo == null || !(userRole == null ||
                ((signUpInfo.get("role").equals("admin") ||
                        signUpInfo.get("role").equals("teacher")) &&
                        userRole.equals("admin")))) {
            logger.warn("someone is with role \"" + userRole +
                    "\" tries to create user with role \"" +
                    signUpInfo.get("role"));
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload index.jsp");
            }
        } else {

            boolean result;
            try {
                result = CustomServiceFactory.getInstance().getCustomService().
                        createUser(signUpInfo);
            } catch (ServiceException e) {
                logger.error("can't create user");
                result = false;
            }

            Properties info;
            try {
                info = CustomServiceFactory.getInstance().
                        getCustomService().getUserByEmail(signUpInfo.getProperty("email"));
            } catch (ServiceException e) {
                logger.error("can't get user info");
                request.setAttribute("info", "u signed up. now go and sign in");
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
                    session.setAttribute("role", signUpInfo.get("role"));
                    session.setAttribute("firstName", signUpInfo.get("firstName"));
                    session.setAttribute("lastName", signUpInfo.get("lastName"));
                    session.setAttribute("email", signUpInfo.get("email"));
                    session.setAttribute("birthday", signUpInfo.get("birthday"));
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

    private Properties formSignUpInfo(HttpServletRequest request) {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");
        String role = request.getParameter("role");
        if (role == null) {
            role = "student";
        }

        Properties signUpInfo;
        if (firstName != null || lastName != null || email != null ||
                password != null || birthday != null) {
            signUpInfo = new Properties();
            signUpInfo.setProperty("firstName", firstName);
            signUpInfo.setProperty("lastName", lastName);
            signUpInfo.setProperty("email", email);
            signUpInfo.setProperty("password", password);
            signUpInfo.setProperty("birthday", birthday);
            signUpInfo.setProperty("role", role);
        } else {
            signUpInfo = null;
        }

        return signUpInfo;
    }
}
