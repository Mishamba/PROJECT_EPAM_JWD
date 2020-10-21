package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class CreateHometaskProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(CreateHometaskProcessCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (!userIsTeacher(request)) {
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("anonym tries to upload task");
            }

            return;
        }

        Properties properties = formPropertiesToCreateHometask(request);

        try {
            CustomServiceFactory.getInstance().getCustomService().createHometask(properties);
        } catch (CustomServiceException e) {
            logger.error("can't create hometask");
            try {
                response.sendRedirect("error.html");
            } catch (IOException ioException) {
                logger.error("can't sent error page");
            }
        }

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload main page");
        }
    }

    private boolean userIsTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("teacher");
    }

    private Properties formPropertiesToCreateHometask(HttpServletRequest request) {
        int courseId;
        try {
            courseId = Integer.parseInt(request.getParameter("course_id"));
        } catch (NumberFormatException | NullPointerException e) {
            logger.error("can't parse course id");
            courseId = 0;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String deadline = request.getParameter("deadline");

        Properties properties = new Properties();

        if (title == null || deadline == null || description == null) {
            logger.warn("can't get important parameters for hometask. " +
                    "all parameters are faked");
            title = "smth";
            deadline = "smth";
            description = "smth";
            courseId = 0;
        }

        properties.setProperty("courseId", String.valueOf(courseId));
        properties.setProperty("title", title);
        properties.setProperty("description", description);
        properties.setProperty("deadline", deadline);

        return properties;
    }
}
