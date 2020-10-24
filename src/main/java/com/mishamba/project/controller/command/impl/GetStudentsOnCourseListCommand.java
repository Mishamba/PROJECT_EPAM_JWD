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

public class GetStudentsOnCourseListCommand implements Command {
    private final Logger logger = Logger.getLogger(GetStudentsOnCourseListCommand.class);
    private final String COURSE_ID = "course_id";
    private final String STUDENTS_LIST_PAGE = "students_list.jsp";
    private final String ERROR_PAGE = "error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = STUDENTS_LIST_PAGE;

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
            logger.warn("can't get course name");
            uploadPage = ERROR_PAGE;
        }

        ArrayList<User> students = new ArrayList<>();
        try {
            students = ServiceFactory.getInstance().getCourseService().
                    getStudentsOnCourse(courseId);
        } catch (CustomServiceException e) {
            logger.error("can't get students");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute("course", course);
        request.setAttribute("students", students);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload students_list.jsp");
        }
    }
}
