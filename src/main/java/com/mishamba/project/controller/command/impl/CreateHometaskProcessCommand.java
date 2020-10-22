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

public class CreateHometaskProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(CreateHometaskProcessCommand.class);
    private final String INDEX_PAGE = "index.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String ROLE = "role";
    private final String TEACHER = "teacher";
    private final String COURSE_ID = "course_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX_PAGE;

        if (!userIsTeacher(request)) {
            uploadPage = ERROR_PAGE;
        }

        Properties properties = formPropertiesToCreateHometask(request);

        try {
            if (ServiceFactory.getInstance().getHometaskService().createHometask(properties)) {
                logger.error("can't create hometask");
                uploadPage = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't create hometask");
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer page");
        }
    }

    private boolean userIsTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(ROLE);
        return role.equals(TEACHER);
    }

    private Properties formPropertiesToCreateHometask(HttpServletRequest request) {
        int courseId;
        try {
            courseId = Integer.parseInt(request.getParameter(COURSE_ID));
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
