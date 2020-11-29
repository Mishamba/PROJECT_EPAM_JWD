package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This class sends page with all active courses and gets
 * this courses from database
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetCoursesCatalogCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCoursesCatalogCommand.class);
    private final String COURSES_CATALOG_PAGE = "pages/courses_catalog.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ID = "id";
    private final String USER = "user";
    private final String COURSES = "courses";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse resp) {
        String uploadPage = COURSES_CATALOG_PAGE;
        Integer userId = (Integer) request.getAttribute(ID);

        Optional<User> user = Optional.empty();
        try {
            if (userId != null) {
                user = ServiceFactory.getInstance().getUserService().getUserById(userId);
            }
        } catch (CustomServiceException e) {
            uploadPage = ERROR_PAGE;
        }

        ArrayList<Course> courses;
        try {
            courses = ServiceFactory.getInstance().getCourseService().
                    getActiveCourses();
        } catch (CustomServiceException e) {
            courses = new ArrayList<>();
        }

        request.setAttribute(USER, user);
        request.setAttribute(COURSES, courses);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            logger.error("can't send courses catalog for user");
        }
    }
}
