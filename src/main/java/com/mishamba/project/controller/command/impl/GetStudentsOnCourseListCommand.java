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

public class GetStudentsOnCourseListCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null || role.equals("admin") || role.equals("student")) {
            logger.error("user with role \"" + role +
                    "\" tries to get students on course list");
            try {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload index.jsp");
            }

            return;
        }

        int courseId = Integer.parseInt(request.getParameter("courseId"));

        String courseName;
        try {
            courseName = CustomServiceFactory.getInstance().getCustomService().
                    getCourseById(courseId).getProperty("courseName");
        } catch (ServiceException e) {
            logger.warn("can't get course name");
            courseName = "<p>can't get course name";
        }

        String students;
        try {
            students = CustomServiceFactory.getInstance().getCustomService().
                    formStudentsOnCourse(courseId);
        } catch (ServiceException e) {
            logger.error("can't get students");
            students = "<p>can't get info</p>";
        }

        request.setAttribute("course_name", courseName);
        request.setAttribute("students", students);

        try {
            request.getRequestDispatcher("students_list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload students_list.jsp");
        }
    }
}