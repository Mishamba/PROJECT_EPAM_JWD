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

public class GetCoursesCatalogCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        if (role == null) {
            role = "anonym";
        }

        Properties userProp = new Properties();
        userProp.setProperty("role", role);
        if (firstName != null && lastName != null) {
            userProp.setProperty("firstName", firstName);
            userProp.setProperty("lastName", lastName);
        }
        userProp.setProperty("target", "user info");

        String userInfo;
        try {
            userInfo = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(userProp);
        } catch (ServiceException e) {
            logger.error("can't upload user info");
            userInfo = "<br><p>can't upload user info</p><br>";
        }

        userProp.setProperty("target", "menu");
        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(userProp);
        } catch (ServiceException e) {
            logger.error("can't upload some menu functionality");
            menu = "<br><p>can't upload some menu buttons";
        }


        String courses;
        try {
            logger.info("getting courses");
            courses = CustomServiceFactory.getInstance().getCustomService().
                    formCoursesCatalog();
        } catch (ServiceException e) {
            courses = "can't upload courses";
        }

        logger.info("setting page attributes");
        request.setAttribute("user_info", userInfo);
        request.setAttribute("menu", menu);
        request.setAttribute("courses", courses);

        try {
            logger.info("uploading courses catalog page");
            request.getRequestDispatcher("courses_catalog.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send courses catalog for user");
        }
    }
}
