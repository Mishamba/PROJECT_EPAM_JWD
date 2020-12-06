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
 * This page sends page with info about course.
 * If user is participated in course he gets some buttons on page
 * for some manage
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetCourseProfileCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCourseProfileCommand.class);
    private final String COURSE_ID = "course_id";
    private final String COURSE_PROFILE_PAGE = "pages/course_profile.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ID = "id";
    private final String COURSE_PARAMETER = "course";
    private final String USER_PARTICIPATION = "participated";
    private final String STUDENTS_ON_COURSE_QUANTITY = "students_on_course_quantity";

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

        Integer userId = (Integer) request.getSession().getAttribute(ID);

        boolean userParticipation = false;

        if (userId != null && course != null) {
            try {
                ArrayList<User> courseStudents = ServiceFactory.getInstance().
                        getCourseService().getStudentsOnCourse(course.getId());
                for (User student : courseStudents) {
                    if (student.getId().equals(userId)) {
                        userParticipation = true;
                        break;
                    }
                }

                if (course.getTeacher() != null && course.getTeacher().getId().equals(userId)) {
                    userParticipation = true;
                }
            } catch (CustomServiceException e) {
                logger.error("can't check is teacher leads the course right now");
            }
        }

        Integer studentsOnCourseQuantity = null;

        try {
            studentsOnCourseQuantity = ServiceFactory.getInstance().
                    getCourseService().getStudentsOnCourse(courseId).size();
        } catch (CustomServiceException e) {
            logger.error("can't get students on course quantity");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(COURSE_PARAMETER, course);
        request.setAttribute(USER_PARTICIPATION, userParticipation);
        request.setAttribute(STUDENTS_ON_COURSE_QUANTITY, studentsOnCourseQuantity);

        try {
            request.getRequestDispatcher(uploadPage).
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload page");
            e.printStackTrace();
        }
    }
}
