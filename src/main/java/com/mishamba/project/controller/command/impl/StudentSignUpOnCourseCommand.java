package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class prodess sign up student on course
 *
 * @version 1.0
 * @author Mishamba
 */

public class StudentSignUpOnCourseCommand implements Command {
    private final Logger logger = Logger.getLogger(StudentSignUpOnCourseCommand.class);
    private final String ID = "id";
    private final String COURSE_ID = "course_id";
    private final String INDEX_PAGE = "index.jsp";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        int studentId = (int) request.getSession().getAttribute(ID);
        int courseId = (int) request.getAttribute(COURSE_ID);

        String uploadPage = INDEX_PAGE;

        try {
            ServiceFactory.getInstance().getCourseService().
                    enterStudentOnCourse(studentId, courseId);
        } catch (CustomServiceException e) {
            logger.error("can't enter student on course");
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload index.jsp");
        }
    }
}
