package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

public class GetAnswerHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetAnswerHometaskPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (!checkForStudent(request)) {
            try {
                request.getRequestDispatcher("error.html").forward(request ,response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page");
            }
            return;
        }

        int hometaskId = getHometaskId(request);

        Hometask hometask;
        try {
            hometask = ServiceFactory.getInstance().getHometaskService().
                    getHometaskById(hometaskId).get();
        } catch (CustomServiceException | NoSuchElementException e) {
            logger.error("can't get hometask info");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException servletException) {
                logger.error("can't upload error.html");
            }

            return;
        }

        request.setAttribute("hometask", hometask);

        try {
            request.getRequestDispatcher("answer_hometask.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer hometask page");
        }
    }

    private boolean checkForStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("student");
    }

    private int getHometaskId(HttpServletRequest request) {
        String courseId = request.getParameter("hometask_id");
        try {
            return Integer.parseInt(courseId);
        } catch (NumberFormatException | NullPointerException e) {
            logger.error("can't parse course id. course id set with 0");
            return 0;
        }
    }
}
