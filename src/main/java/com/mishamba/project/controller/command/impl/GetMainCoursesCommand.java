package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetMainCoursesCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String coursesAdd = null;
        try {
            coursesAdd = CustomServiceImpl.getInstance().formMainCourses();
        } catch (ServiceException e) {
            coursesAdd = "can't upload courses";
        }
        req.setAttribute("courses_add", coursesAdd);
        try {
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send main page for user");
        }
    }
}
