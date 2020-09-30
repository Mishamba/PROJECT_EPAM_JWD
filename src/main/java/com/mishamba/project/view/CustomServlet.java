package com.mishamba.project.view;

import com.mishamba.project.controller.Invoker;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class CustomServlet extends HttpServlet {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("got connection");
        try {
            Properties properties = new Properties();
            properties.setProperty("commandType", "mainCourses");
            String add = Invoker.getInstance().executeCommand(properties);
            req.setAttribute("courses_add", add);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
