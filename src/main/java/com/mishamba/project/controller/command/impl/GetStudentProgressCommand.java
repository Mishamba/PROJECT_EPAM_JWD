package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Hometask;
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

public class GetStudentProgressCommand implements Command {
    private final Logger logger = Logger.getLogger(GetStudentProgressCommand.class);
    private final String STUDENT_ID = "student_id";
    private final String COURSE_ID = "course_id";
    private final String TEACHER_ROLE = "teacher";
    private final String STUDENT_PARAMETER = "student";
    private final String HOMETASK_PARAMETER = "hometask";
    private final String STUDENT_PROGRESS_PAGE = "pages/student_progress.jsp";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = STUDENT_PROGRESS_PAGE;

        int studentId = (int) request.getAttribute(STUDENT_ID);

        User student = null;
        try {
            Optional<User> optionalStudent;
            optionalStudent = ServiceFactory.getInstance().
                    getUserService().getUserById(studentId);
            if (optionalStudent.isPresent()) {
                student = optionalStudent.get();
            }
        } catch (CustomServiceException e) {
            logger.error("can't get student info");
            uploadPage = ERROR_PAGE;
        }

        int courseId = (int) request.getAttribute(COURSE_ID);

        ArrayList<Hometask> hometask = new ArrayList<>();
        try {
            hometask = ServiceFactory.getInstance().getHometaskService().
                    getCourseHometaskForUser(courseId, studentId, TEACHER_ROLE);
        } catch (CustomServiceException e) {
            logger.error("can't get hometask list");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(STUDENT_PARAMETER, student);
        request.setAttribute(HOMETASK_PARAMETER, hometask);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload student progress page");
        }
    }
}
