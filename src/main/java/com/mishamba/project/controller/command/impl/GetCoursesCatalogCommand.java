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

public class GetCoursesCatalogCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCoursesCatalogCommand.class);
    private final String COURSES_CATALOG_PAGE = "pages/courses_catalog.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse resp) {
        String uploadPage = COURSES_CATALOG_PAGE;

        User user = null;
        try {
            Optional<User> optionalUser;
            optionalUser = ServiceFactory.getInstance().getUserService().getUserById((int) request.getSession().getAttribute(ID));
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
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

        request.setAttribute("user_info", user);
        request.setAttribute("courses", courses);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send courses catalog for user");
        }
    }
}
