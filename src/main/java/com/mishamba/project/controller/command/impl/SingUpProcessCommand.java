package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
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
        if (!(userRole == null || ((singUpInfo.get("role").equals("admin") ||
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
                result = CustomServiceImpl.getInstance().createUser(singUpInfo);
            } catch (ServiceException e) {
                logger.error("can't create user");
                result = false;
            }

            String pageToLoad;
            if (result) {
                if (userRole == null) {
                    session.setAttribute("role", singUpInfo.get("role"));
                    session.setAttribute("firstName", singUpInfo.get("firstName"));
                    session.setAttribute("lastName", singUpInfo.get("lastName"));
                }
                pageToLoad = "main.jsp";
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

        Properties singUpInfo = new Properties();
        singUpInfo.setProperty("firstName", firstName);
        singUpInfo.setProperty("lastName", lastName);
        singUpInfo.setProperty("email", email);
        singUpInfo.setProperty("password", password);
        singUpInfo.setProperty("birthday", birthday);
        singUpInfo.setProperty("role", role);

        return singUpInfo;
    }
}
