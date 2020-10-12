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

public class GetCourseHometaskPageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();
    private final String ROLE = "role";
    private final String TARGET = "target";
    private final String MENU = "menu";
    private final String COURSE_ID = "courseId";
    private final String MENU_ERROR_SIGN = "<p>can't get menu</p>";
    private final String HOMETASK_ERROR_SIGN = "<p>can't get hometasks list</p>";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String pageToUpload = "course_hometask_page.jsp";
        if (checkForAnonym(req)) {
            logger.warn("anonym tries to get course hometask");
            pageToUpload = "error.html";
        }

        Properties properties = formProperties(req);

        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't get menu buttons");
            menu = MENU_ERROR_SIGN;
        }

        int courseId = getCourseId(req);

        String hometasks;
        try {
            hometasks = CustomServiceFactory.getInstance().getCustomService().getCourseHometask(courseId);
        } catch (CustomServiceException e) {
            hometasks = HOMETASK_ERROR_SIGN;
        }

        req.setAttribute("menu", menu);
        req.setAttribute("hometasks", hometasks);

        try {
            req.getRequestDispatcher("course_hometask_page.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send answer page");
        }
    }

    private boolean checkForAnonym(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(ROLE);

        return (role == null);
    }

    private Properties formProperties(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(ROLE);
        String courseId = req.getParameter("course_id");

        Properties properties = new Properties();
        properties.setProperty(ROLE, role);
        properties.setProperty(TARGET, MENU);
        properties.setProperty(COURSE_ID, courseId);

        return properties;
    }

    private int getCourseId(HttpServletRequest req) {
        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter("courseId"));
        } catch (NullPointerException | NumberFormatException e) {
            logger.warn("can't get course id from request");
            courseId = 0;
        }

        return courseId;
    }
}
