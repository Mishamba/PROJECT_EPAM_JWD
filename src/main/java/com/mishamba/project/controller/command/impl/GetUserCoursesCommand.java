package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Course;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class sends info about courses user participated in.
 * Also this class filters this courses by finished parameter
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetUserCoursesCommand implements Command {
    private final Logger logger = Logger.getLogger(GetUserCoursesCommand.class);
    private final String ID = "id";
    private final String ROLE = "role";
    private final String FINISHED = "finished";
    private final String USER_COURSES_PAGE = "pages/user_courses_page.jsp";
    private final String COURSES_PARAMETER = "courses";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = USER_COURSES_PAGE;

        int userId = (int) request.getSession().getAttribute(ID);
        String role = (String) request.getSession().getAttribute(ROLE);
        boolean finished = (boolean) request.getAttribute(FINISHED);

        List<Course> courses = new ArrayList<>();
        try {
            courses = ServiceFactory.getInstance().getCourseService().
                    getUserCourses(userId, role, finished);
        } catch (CustomServiceException e) {
            logger.error("can't get user courses list");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(COURSES_PARAMETER, courses);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload courses list page");
        }
    }
}
