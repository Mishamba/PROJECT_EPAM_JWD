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

public class GetUserCoursesCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getting and checking info sent by user");
        HttpSession session = request.getSession();
        int id = (int) session.getAttribute("id");
        String role = (String) session.getAttribute("role");
        String finished = (String) request.getAttribute("finished");

        if (role == null || role.equals("admin") || finished == null) {
            logger.warn("anonym user or admin or someone who didn't send " +
                    "finish parameter tries to get active courses");

            try {
                request.getRequestDispatcher("error.html").
                        forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't send error page");
            }

            return;
        }

        logger.info("user send correct info to ");

        Properties properties = new Properties();
        properties.setProperty("userId", String.valueOf(id));
        properties.setProperty("finished", finished);

        String courses;
        try {
            courses = CustomServiceFactory.getInstance().getCustomService().
                    formUserCourses(properties);
        } catch (ServiceException e) {
            logger.error("can't get user courses list");
            courses = "<p>can't get your courses list</p>";
        }

        request.setAttribute("courses", courses);

        try {
            request.getRequestDispatcher("user_courses_list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload courses list page");
        }
    }
}
