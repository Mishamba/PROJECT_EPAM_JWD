package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetMainPageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String userInfo;
        HttpSession session = req.getSession();
        logger.info("getting user role");
        String role = (String) session.getAttribute("role");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        if (role == null) {
            logger.info("user has no role, so role set as \"anonym\"");
            role = "anonym";
        }
        Properties properties = new Properties();
        if (firstName != null && lastName != null) {
            properties.setProperty("firstName", firstName);
            properties.setProperty("lastName", lastName);
        }
        properties.setProperty("role", role);
        properties.setProperty("target", "user info");
        try {
            logger.info("getting user info");
            userInfo = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
        } catch (ServiceException e) {
            logger.error("can't get user info");
            userInfo = "can't upload user info";
        }

        String menu;
        properties.setProperty("target", "menu");
        try {
            logger.info("getting menu buttons");
            menu = CustomServiceFactory.getInstance().getCustomService().formPageParameter(properties);
        } catch (ServiceException e) {
            logger.error("can't get menu buttons");
            menu = "can't upload menu (((";
        }

        String coursesAdd;
        try {
            logger.info("getting courses add");
            coursesAdd = CustomServiceFactory.getInstance().getCustomService().
                    formMainCourses();
        } catch (ServiceException e) {
            coursesAdd = "can't get courses add ((((";
        }

        logger.info("setting jsp page attributes");
        req.setAttribute("user_info", userInfo);
        req.setAttribute("menu", menu);
        req.setAttribute("courses_add", coursesAdd);

        try {
            logger.info("uploading main page");
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send response for user");
        }
    }
}
