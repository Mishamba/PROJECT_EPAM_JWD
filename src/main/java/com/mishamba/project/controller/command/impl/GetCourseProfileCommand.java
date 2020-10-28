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

public class GetCourseProfileCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCourseProfileCommand.class);
    private final String COURSE_ID = "course_id";
    private final String COURSE_PROFILE_PAGE = "pages/course_profile.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ID = "id";
    private final String COURSE_PARAMETER = "course";
    private final String USER_PARTICIPATION = "participation";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = COURSE_PROFILE_PAGE;
        int courseId = (int) request.getAttribute(COURSE_ID);

        Course course = null;
        try {
            Optional<Course> optionalCourse;
            optionalCourse = ServiceFactory.getInstance().getCourseService().
                    getCourseById(courseId);
            if (optionalCourse.isPresent()) {
                course = optionalCourse.get();
            }
        } catch (CustomServiceException e) {
            uploadPage = ERROR_PAGE;
        }

        int userId = (int) request.getSession().getAttribute(ID);

        boolean userParticipaion = false;
        try {
            Optional<Course> optionalCourse = ServiceFactory.getInstance().
                    getCourseService().getCourseById(courseId);
            ArrayList<User> courseStudents = ServiceFactory.getInstance().
                    getCourseService().getStudentsOnCourse(courseId);
            for (User student : courseStudents) {
                if (student.getId() == userId) {
                    userParticipaion = true;
                    break;
                }
            }
            if (optionalCourse.isPresent() && !userParticipaion) {
                userParticipaion = optionalCourse.get().getTeacher().getId() == userId;
            }
        } catch (CustomServiceException e) {
            logger.error("can't check is teacher leads the course right now");
        }

        request.setAttribute(COURSE_PARAMETER, course);
        request.setAttribute(USER_PARTICIPATION, userParticipaion);

        try {
            request.getRequestDispatcher(uploadPage).
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload page");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException exception) {
                logger.error("can't upload error page");
            }
        }
    }
}
