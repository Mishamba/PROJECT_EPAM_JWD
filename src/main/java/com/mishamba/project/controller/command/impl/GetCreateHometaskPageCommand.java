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

public class GetCreateHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCreateHometaskPageCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        String menu;
        try {
            menu = ServiceFactory.getInstance().getCustomService().
                    formPageParameter(formMenuProperties(req));
        } catch (CustomServiceException e) {
            logger.error("can't get menu");
            menu = "<p>can't get menu";
        }

        try {
            if (checkForAnonym(req)) {
                req.getRequestDispatcher("error.html").forward(req, resp);
                return;
            }

            String courseId = getCourseId(req);

            req.setAttribute("menu" ,menu);
            req.setAttribute("course_id", courseId);

            logger.info("uploading create hometask page");
            req.getRequestDispatcher("create_hometask.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't upload create hometask page");
        }
    }

    private boolean checkForAnonym(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (session.getAttribute("role") == null);
    }

    private String getCourseId(HttpServletRequest request) {
        String courseId = request.getParameter("course_id");
        if (courseId == null) {
            logger.warn("given course id is null, so setting fake course id");
            courseId = "0";
        }

        return courseId;
    }

    private Properties formMenuProperties(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Properties properties = new Properties();

        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        return properties;
    }
}
