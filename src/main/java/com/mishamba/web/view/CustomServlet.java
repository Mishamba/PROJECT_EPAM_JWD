package com.mishamba.web.view;

import com.mishamba.web.controller.exception.ControllerException;
import com.mishamba.web.controller.impl.CustomControllerImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomServlet extends HttpServlet {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("got connection");
        try {
            String add = CustomControllerImpl.getInstance().execute();
            req.setAttribute("courses_add", add);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (ServletException | IOException | ControllerException e) {
            e.printStackTrace();
        }
    }
}
