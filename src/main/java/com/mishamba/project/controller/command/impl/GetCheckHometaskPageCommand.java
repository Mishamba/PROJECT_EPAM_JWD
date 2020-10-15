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

public class GetCheckHometaskPageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (checkForTeacher(request)) {
            try {
                request.getRequestDispatcher("error.html").forward(request ,response);
            } catch (ServletException | IOException e) {
                logger.error("can't send upload");
            }

            return;
        }

        Properties properties = formProperties(request);

        String menu;
        try {
            menu = CustomServiceFactory.getInstance().getCustomService().
                    formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't get menu");
            menu = "<p>can't get menu</p>";
        }

        String hometask;
        hometask =
    }

    private boolean checkForTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        return role.equals("teacher");
    }

    private Properties formProperties(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        String hometaskId = (String) request.getParameter("hometask_id");

        if (hometaskId == null) {
            logger.warn("no hometask id given. setting fake id");
            hometaskId = "0";
        }

        Properties properties = new Properties();
        properties.setProperty("role", role);
        properties.setProperty("target", "menu");

        return properties;
    }
}
