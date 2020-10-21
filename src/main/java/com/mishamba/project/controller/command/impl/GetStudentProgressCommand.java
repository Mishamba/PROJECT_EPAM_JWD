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

public class GetStudentProgressCommand implements Command {
    private final Logger logger = Logger.getLogger(GetStudentProgressCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (!checkForTeacher(request)) {
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page");
            }

            return;
        }

        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(formProperties(request));
        } catch (CustomServiceException e) {
            logger.error("can't get menu");
            menu = "<p>can't get menu</p>";
        }

        int studentId = getStudentId(request);

        String studentInfo;
        try {
            studentInfo = CustomServiceFactory.getInstance().
                    getCustomService().getUserInfoById(studentId);
        } catch (CustomServiceException e) {
            logger.error("can't get student info");
            studentInfo = "<p> can't get student info</p>";
        }

        int courseId = getCourseId(request);

        String hometask;
        try {
            hometask = CustomServiceFactory.getInstance().getCustomService().
                    getCourseHometaskForUser(courseId, studentId, "teacher");
        } catch (CustomServiceException e) {
            logger.error("can't get hometask list");
            hometask = "<p>can't get hometasks</p>";
        }

        request.setAttribute("menu", menu);
        request.setAttribute("student_info", studentInfo);
        request.setAttribute("hometask", hometask);

        try {
            request.getRequestDispatcher("student_progress.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload student progress page");
        }
    }

    private boolean checkForTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("teacher");
    }

    private int getCourseId(HttpServletRequest request) {
        String courseId = request.getParameter("course_id");
        try {
            return Integer.parseInt(courseId);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("can't parse course id. set 0");
            return 0;
        }
    }

    private int getStudentId(HttpServletRequest request) {
        String studentId = request.getParameter("student_id");
        try {
            return Integer.parseInt(studentId);
        } catch (NumberFormatException | NullPointerException e) {
            logger.error("can't parse student id. set 0");
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
