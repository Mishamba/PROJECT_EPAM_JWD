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

public class GetCoursesCatalogCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        Properties userProp = formProperties(req);

        String userInfo;
        try {
            userInfo = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(userProp);
        } catch (CustomServiceException e) {
            logger.error("can't upload user info");
            userInfo = "<br><p>can't upload user info</p><br>";
        }

        userProp.setProperty("target", "menu");
        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(userProp);
        } catch (CustomServiceException e) {
            logger.error("can't upload some menu functionality");
            menu = "<br><p>can't upload some menu buttons";
        }


        String courses;
        try {
            logger.info("getting courses");
            courses = CustomServiceFactory.getInstance().getCustomService().
                    getCoursesCatalog();
        } catch (CustomServiceException e) {
            courses = "can't upload courses";
        }

        logger.info("setting page attributes");
        req.setAttribute("user_info", userInfo);
        req.setAttribute("menu", menu);
        req.setAttribute("courses", courses);

        try {
            logger.info("uploading courses catalog page");
            req.getRequestDispatcher("courses_catalog.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send courses catalog for user");
        }
    }

    private Properties formProperties(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        String localization = (String) session.getAttribute("localization");
        if (role == null) {
            role = "anonym";
        }

        Properties userProp = new Properties();
        userProp.setProperty("role", role);
        if (firstName != null && lastName != null) {
            userProp.setProperty("firstName", firstName);
            userProp.setProperty("lastName", lastName);
            userProp.setProperty("localization", localization);
        }
        userProp.setProperty("target", "user info");

        return userProp;
    }
}
