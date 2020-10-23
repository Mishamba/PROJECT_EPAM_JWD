package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetKickStudentOutPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(GetKickStudentOutPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = (checkForTeacher(request)) ? "kick_out_page.jsp" : "error.html";

        Properties menuProperties = formProperties(request);
        String menu;
        try {
            menu = ServiceFactory.getInstance().getCustomService().formPageParameter(menuProperties);
        } catch (CustomServiceException e) {
            logger.error("can't get menu");
            menu = "<p>can't get menu</p>";
        }

        int studentId = getStudentId(request);

        String studentInfo;
        try {
            studentInfo = ServiceFactory.getInstance().getCustomService().getUserInfoById(studentId);
        } catch (CustomServiceException e) {
            logger.error("can't get user info");
            studentInfo = "<p>can't get user info<p>";
        }

        request.setAttribute("menu", menu);
        request.setAttribute("student_info", studentInfo);

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer page");
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

    private boolean checkForTeacher(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        return role.equals("teacher");
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
}
