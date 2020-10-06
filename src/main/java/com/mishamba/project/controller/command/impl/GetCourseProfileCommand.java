package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetCourseProfileCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int courseId;
        try {
                courseId = Integer.parseInt(request.getParameter("course_id"));
        } catch (NumberFormatException exception) {
            logger.warn("can't find course id for course profile");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error("can't upload error page for user");
            }

            return;
        }

        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        String menu;
        try {
            menu = CustomServiceImpl.getInstance().
                    formPageParameter(properties);
            logger.info("got menu buttons code");
        } catch (ServiceException e) {
            logger.error("cant' get menu buttons for course profile");
            menu = "can't get menu buttons";
        }

        String courseInfo;
        try {
            courseInfo = CustomServiceImpl.getInstance().formCourseProfile(courseId);
            logger.info("got course info");
        } catch (ServiceException e) {
            courseInfo = "<p>Can't get course info</p>";
        }

        properties.setProperty("target", "course profile button");
        String buttons;
        try {
            buttons = CustomServiceImpl.getInstance().formPageParameter(properties);
            logger.info("got buttons for couse profile");
        } catch (ServiceException e) {
            logger.error("can't get course action buttons");
            buttons = "can't get buttons";
        }

        request.setAttribute("menu", menu);
        request.setAttribute("course_info", courseInfo);
        request.setAttribute("buttons", buttons);

        try {
            request.getRequestDispatcher("course_profile.jsp").
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload course profile page");
            try {
                request.getRequestDispatcher("error.html").forward(request, response);
            } catch (ServletException | IOException exception) {
                logger.error("can't upload error page");
            }
        }
    }
}
