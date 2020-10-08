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

public class GetCourseProfileCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        String menu = formMenu(properties);

        int courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("course_id"));
        } catch (NumberFormatException | NullPointerException exception) {
            logger.warn("can't find course id for course profile");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page for user");
            }

            return;
        }

        String courseInfo;
        try {
            courseInfo = CustomServiceFactory.getInstance().getCustomService().
                    formCourseProfile(courseId);
            logger.info("got course info");
        } catch (ServiceException e) {
            courseInfo = "<p>Can't get course info</p>";
        }

        int userId;
        try {
            userId = (int) session.getAttribute("id");
        } catch (NullPointerException | NumberFormatException e) {
            logger.warn("can't get user id");
            userId = 0;
        }

        formPropertiesToButtonForm(properties, role, userId, courseId);

        String buttons = formButtons(properties);

        request.setAttribute("menu", menu);
        request.setAttribute("course_info", courseInfo);
        request.setAttribute("buttons", buttons);

        try {
            request.getRequestDispatcher("course_profile.jsp").
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload course profile page");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException exception) {
                logger.error("can't upload error page");
            }
        }
    }

    private String formMenu(Properties properties) {
        String menu;

        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
            logger.info("got menu buttons code");
        } catch (ServiceException e) {
            logger.error("cant' get menu buttons for course profile");
            menu = "can't get menu buttons";
        }

        return menu;
    }

    private void formPropertiesToButtonForm(Properties properties,
                                            String role, int userId,
                                            int courseId) {
        try {
            if ((role.equals("student") && CustomServiceFactory.getInstance().
                    getCustomService().isStudentOnCourse(userId, courseId)) ||
                    (role.equals("teacher") && CustomServiceFactory.getInstance().
                            getCustomService().
                            isTeacherLeadsCourse(userId, courseId))) {
                properties.setProperty("target", "on course profile button");
                properties.setProperty("courseId", String.valueOf(courseId));
                properties.setProperty("userId", String.valueOf(userId));
            } else {
                properties.setProperty("target", "not on course profile button");
            }
            logger.info("set properties");
        } catch (ServiceException e) {
            logger.error("can't set properties");
            properties.setProperty("target", "not on course profile button");
        }
    }

    private String formButtons(Properties properties) {
        String buttons;
        try {
            buttons = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
            logger.info("got buttons for couse profile");
        } catch (ServiceException e) {
            logger.error("can't get course action buttons");
            buttons = "can't get buttons";
        }

        return buttons;
    }
}
