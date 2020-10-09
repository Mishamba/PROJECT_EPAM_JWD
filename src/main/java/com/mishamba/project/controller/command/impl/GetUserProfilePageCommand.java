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

public class GetUserProfilePageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        String birthday = (String) session.getAttribute("birthday");
        String email = (String) session.getAttribute("email");
        int userId = (int) session.getAttribute("id");
        if (role == null || firstName == null || lastName == null ||
                birthday == null || email == null) {
            logger.warn("user without role tries to get profile page");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't load error page");
            }
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");
        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
        } catch (ServiceException e) {
            logger.warn("can't get menu buttons");
            menu = "<p>can't get menu buttons</p>";
        }

        String activeCoursesButtonSign = (role.equals("teacher")) ?
                "Managing Courses" : (role.equals("student")) ?
                "Active courses" : null;
        String passedCoursesButtonSign = (role.equals("teacher")) ?
                "Managed Courses" : (role.equals("student")) ?
                "Passed courses" : null;

        properties.setProperty("target", "profile");
        properties.setProperty("activeCoursesButtonSign", activeCoursesButtonSign);
        properties.setProperty("passedCoursesButtonSign", passedCoursesButtonSign);
        properties.setProperty("userId", String.valueOf(userId));

        String courses_buttons;
        try {
            courses_buttons = CustomServiceFactory.getInstance().
                    getCustomService().formPageParameter(properties);
        } catch (ServiceException e) {
            logger.error("can't get course buttons for profile");
            courses_buttons = "can't get buttons";
        }

        request.setAttribute("role", role);
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("birthday", birthday);
        request.setAttribute("email", email);
        request.setAttribute("courses_buttons", courses_buttons);
        request.setAttribute("menu", menu);

        try {
            request.getRequestDispatcher("user_profile.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send profile page");
        }
    }
}
