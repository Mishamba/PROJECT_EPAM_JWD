package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetCoursesCatalogCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String courses = null;
        try {
            logger.info("getting courses");
            courses = CustomServiceImpl.getInstance().formCoursesCatalog();
        } catch (ServiceException e) {
            courses = "can't upload courses";
        }

        logger.info("setting page attributes");
        request.setAttribute("courses", courses);

        try {
            logger.info("uploading courses catalog page");
            request.getRequestDispatcher("courses_catalog.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send courses catalog for user");
        }
    }
}
