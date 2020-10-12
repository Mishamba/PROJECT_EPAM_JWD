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

public class GetCourseProfileCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse response) { // TODO: 10/12/20 refactor
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        String menu = formMenu(properties);

        int courseId = getCourseId(req);

        String courseInfo;
        try {
            courseInfo = CustomServiceFactory.getInstance().getCustomService().
                    getCourseProfile(courseId);
            logger.info("got course info");
        } catch (CustomServiceException e) {
            courseInfo = "<p>Can't get course info</p>";
        }

        int userId = getUserId(req);

        boolean teacherLeads;
        try {
            teacherLeads = (Integer.parseInt(CustomServiceFactory.getInstance().
                    getCustomService().getCourseById(courseId).
                    getProperty("teacherId")) == userId);
        } catch (CustomServiceException e) {
            logger.warn("can't check is teacher leads the course right now");
            teacherLeads = false;
        }

        formPropertiesToButtonForm(properties, role, userId, courseId, teacherLeads);

        String buttons = formButtons(properties);

        req.setAttribute("menu", menu);
        req.setAttribute("course_info", courseInfo);
        req.setAttribute("buttons", buttons);

        try {
            req.getRequestDispatcher("course_profile.jsp").
                    forward(req, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload course profile page");
            try {
                req.getRequestDispatcher("error.html").forward(req, response);
            } catch (ServletException | IOException exception) {
                logger.error("can't upload error page");
            }
        }
    }

    private String formMenu(Properties properties) {
        String menu;

        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
            logger.info("got menu buttons code");
        } catch (CustomServiceException e) {
            logger.error("cant' get menu buttons for course profile");
            menu = "can't get menu buttons";
        }

        return menu;
    }

    private void formPropertiesToButtonForm(Properties properties,
                                            String role, int userId,
                                            int courseId, boolean teacherLeads) {
        try {
            if ((role.equals("student") && CustomServiceFactory.getInstance().
                    getCustomService().isStudentOnCourse(userId, courseId)) ||
                    (role.equals("teacher") && teacherLeads)) {
                properties.setProperty("target", "on course profile button");
                properties.setProperty("courseId", String.valueOf(courseId));
                properties.setProperty("userId", String.valueOf(userId));
            } else {
                properties.setProperty("target", "not on course profile button");
            }
            logger.info("set properties");
        } catch (CustomServiceException e) {
            logger.error("can't set properties");
            properties.setProperty("target", "not on course profile button");
        }
    }

    private String formButtons(Properties properties) {
        String buttons;
        try {
            buttons = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
            logger.info("got buttons for couse profile");
        } catch (CustomServiceException e) {
            logger.error("can't get course action buttons");
            buttons = "can't get buttons";
        }

        return buttons;
    }

    private int getCourseId(HttpServletRequest req) {
        int courseId;

        try {
            courseId = Integer.parseInt(req.getParameter("course_id"));
        } catch (NumberFormatException | NullPointerException exception) {
            logger.warn("can't find course id for course profile");
            courseId = 0;
        }

        return courseId;
    }

    private int getUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        int userId;

        try {
            userId = (int) session.getAttribute("id");
        } catch (NullPointerException | NumberFormatException e) {
            logger.warn("can't get user id");
            userId = 0;
        }

        return userId;
    }
}
