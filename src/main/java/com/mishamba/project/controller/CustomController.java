package com.mishamba.project.controller;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.provider.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomController extends HttpServlet {
    private final Logger logger = Logger.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("got GET request");

        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("got POST request");

        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String commandType = req.getParameter("command");
        logger.info("got commandType");
        Command command = CommandProvider.getInstance().getCommand(commandType);
        if (command != null) {
            command.execute(req, resp);
            logger.info("command executed");
        } else {
            logger.warn("can't get command, so uploading error page");
            req.getRequestDispatcher("error.html").forward(req, resp);
        }
    }
}
