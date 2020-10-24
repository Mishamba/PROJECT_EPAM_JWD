package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetKickStudentOutPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(GetKickStudentOutPageCommand.class);
    private final String KICK_OUT_PAGE = "kick_out_page.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String STUDENT_ID = "student_id";
    private final String STUDENT_PARAMETER = "student";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = KICK_OUT_PAGE;

        int studentId = (int) request.getAttribute(STUDENT_ID);

        User student = null;
        try {
            Optional<User> optionalStudent;
            optionalStudent = ServiceFactory.getInstance().getUserService().getUserById(studentId);
            if (optionalStudent.isPresent()) {
                student = optionalStudent.get();
            }
        } catch (CustomServiceException e) {
            logger.error("can't get user info");
            pageToUpload = ERROR_PAGE;
        }

        request.setAttribute(STUDENT_PARAMETER, student);

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer page");
        }
    }
}
