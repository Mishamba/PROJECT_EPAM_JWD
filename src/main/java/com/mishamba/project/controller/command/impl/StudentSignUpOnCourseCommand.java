package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StudentSignUpOnCourseCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("student")) {
            sendErrorPageWithMessage(request, response,
                    "your role isn't student");
            return;
        }

        int studentId = (Integer) session.getAttribute("id");
        int courseId = Integer.parseInt(request.getParameter("course_id"));

        boolean result;
        try {
            result = CustomServiceFactory.getInstance().getCustomService().
                    enterStudentOnCourse(studentId, courseId);
        } catch (ServiceException e) {
            logger.error("can't enter student on course");
            sendErrorPageWithMessage(request, response,
                    "can't enter you on course");
            return;
        }

        if (result) {
            logger.error("can't enter student on course");
            sendErrorPageWithMessage(request, response,
                    "can't enter you on course");
            return;
        }

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload index.jsp");
        }
    }

    private void sendErrorPageWithMessage(HttpServletRequest request,
                                          HttpServletResponse response,
                                          String message) {
        if (message == null) {
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page");
            }
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("<p>").append(message).append("</p>");
            request.setAttribute("info", builder.toString());
            try {
                request.getRequestDispatcher("info_page_with_parameter.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload page with error info");
            }
        }
    }
}
