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

public class GetMainPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetMainPageCommand.class);
    private final String TARGET = "target";
    private final String MENU = "menu";
    private final String ROLE = "role";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String ANONYM_ROLE = "anonym";
    private final String USER_INFO = "user info";
    private final String USER_INFO_PARAMETER = "user_info";
    private final String MENU_PARAMETER = "menu";
    private final String COURSES_ADD_PARAMETER = "courses_add";
    private final String USER_INFO_ERROR_SIGN = "can't upload error sign";
    private final String MENU_ERROR_SIGN = "can't upload menu";
    private final String COURSES_ADD_ERROR_SIGN = "can't get courses add";
    private final String MAIN_JSP = "main.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String userInfo;
        Properties properties = formPropertiesForMainPage(req);

        try {
            logger.info("getting user info");
            userInfo = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't get user info");
            userInfo = USER_INFO_ERROR_SIGN;
        }

        String menu;
        properties.setProperty(TARGET, MENU);
        try {
            logger.info("getting menu buttons");
            menu = CustomServiceFactory.getInstance().getCustomService().formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't get menu buttons");
            menu = MENU_ERROR_SIGN;
        }

        String coursesAdd;
        try {
            logger.info("getting courses add");
            coursesAdd = CustomServiceFactory.getInstance().getCustomService().
                    getMainCourses();
        } catch (CustomServiceException e) {
            coursesAdd = COURSES_ADD_ERROR_SIGN;
        }

        logger.info("setting jsp page attributes");
        req.setAttribute(USER_INFO_PARAMETER, userInfo);
        req.setAttribute(MENU_PARAMETER, menu);
        req.setAttribute(COURSES_ADD_PARAMETER, coursesAdd);

        try {
            logger.info("uploading main page");
            req.getRequestDispatcher(MAIN_JSP).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send response for user");
        }
    }

    private Properties formPropertiesForMainPage(HttpServletRequest req) {
        HttpSession session = req.getSession();
        logger.info("getting user role");
        String role = (String) session.getAttribute(ROLE);
        String firstName = (String) session.getAttribute(FIRST_NAME);
        String lastName = (String) session.getAttribute(LAST_NAME);
        if (role == null) {
            logger.info("user has no role, so role set as \"anonym\"");
            role = ANONYM_ROLE;
        }

        Properties properties = new Properties();

        if (firstName != null && lastName != null) {
            properties.setProperty(FIRST_NAME, firstName);
            properties.setProperty(LAST_NAME, lastName);
        }
        properties.setProperty(ROLE, role);
        properties.setProperty(TARGET, USER_INFO);

        return properties;
    }
}
