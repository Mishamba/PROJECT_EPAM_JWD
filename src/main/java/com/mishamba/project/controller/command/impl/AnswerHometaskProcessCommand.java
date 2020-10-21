package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AnswerHometaskProcessCommand implements Command {
    private final static Logger logger = Logger.getLogger(AnswerHometaskProcessCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (!checkForStudent(request)) {
            logger.warn("user with not student role tries to upload hometask answer");
            try {
                request.getRequestDispatcher("error.html").forward(request ,response);
            } catch (ServletException | IOException e) {
                logger.error("can't send error page");
            }

            return;
        }

        int hometaskId = getHometaskId(request);
        String answer = request.getParameter("answer");
        int studentId = getUserId(request);

        try {
            CustomServiceFactory.getInstance().getCustomService().writeHometaskAnswer(answer, hometaskId, studentId);
        } catch (CustomServiceException e) {
            logger.error("can't write answer");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException servletException) {
                logger.error("can't send error page");
            }
        }

        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send main page");
        }
    }

    private boolean checkForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("student");
    }

    private int getHometaskId(HttpServletRequest request) {
        String hometaskId = request.getParameter("hometask_id");
        try {
            return Integer.parseInt(hometaskId);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("can't parse hometask id. setting 0");
            return 0;
        }
    }

    private int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");
        return userId;
    }
}
