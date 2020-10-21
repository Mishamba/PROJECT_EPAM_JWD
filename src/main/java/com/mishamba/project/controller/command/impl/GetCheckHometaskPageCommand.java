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
import java.util.Properties;

public class GetCheckHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCheckHometaskPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String answerPage = "check_hometask.jsp";
        if (!checkForTeacher(request)) {
            answerPage = "error.html";
        }

        Properties properties = formProperties(request);

        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't get menu");
            menu = "<p>can't get menu</p>";
        }

        int hometaskId = getHometaskId(request);
        int studentId = getStudentId(request);

        String hometaskData;
        try {
            hometaskData = CustomServiceFactory.getInstance().getCustomService().getStudentHometaskWithResponce(hometaskId, studentId);
        } catch (CustomServiceException e) {
            answerPage = "error.html";
            hometaskData = "<p>can't get hometask data</p>";
        }

        request.setAttribute("menu", menu);
        request.setAttribute("hometask_data", hometaskData);

        try {
            request.getRequestDispatcher(answerPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send answer page");
        }
    }

    private boolean checkForTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("teacher");
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

    private int getStudentId(HttpServletRequest request) {
        String studentId = request.getParameter("student_id");
        try {
            return Integer.parseInt(studentId);
        } catch (NumberFormatException | NullPointerException e) {
            logger.error("can't parse student id. setting 0");
            return 0;
        }
    }

    private Properties formProperties(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        return properties;
    }
}
