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

public class GetMainPageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String userInfo = null;
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }
        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("page", "main");
        properties.setProperty("target", "user info");
        try {
            userInfo = CustomServiceImpl.getInstance().formPageParameter(properties);
        } catch (ServiceException e) {
            logger.error("can't get user info");
            userInfo = "can't upload user info";
        }

        String menu = null;
        properties.setProperty("target", "menu");
        try {
            menu = CustomServiceImpl.getInstance().formPageParameter(properties);
        } catch (ServiceException e) {
            logger.error("can't get menu buttons");
            menu = "can't upload menu (((";
        }

        String coursesAdd;
        try {
            coursesAdd = CustomServiceImpl.getInstance().formMainCourses();
        } catch (ServiceException e) {
            coursesAdd = "can't get courses add ((((";
        }

        req.setAttribute("user_info", userInfo);
        req.setAttribute("menu", menu);
        req.setAttribute("courses_add", coursesAdd);

        try {
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send response for user");
        }
    }
}
