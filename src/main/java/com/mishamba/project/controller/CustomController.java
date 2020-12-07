package com.mishamba.project.controller;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.provider.GETCommandProvider;
import com.mishamba.project.controller.command.provider.POSTCommandProvider;
import com.mishamba.project.filter.right.RightsHolder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomController extends HttpServlet {
    private final Logger logger = Logger.getLogger(CustomController.class);
    private final String POST = "POST";
    private final String GET = "GET";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("got GET request");

        process(req, resp, GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("got POST request");

        process(req, resp, POST);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp, String requestType)
            throws ServletException, IOException {
        String commandType = req.getParameter("command");
        logger.info("got commandType");
        String role = (String) req.getSession().getAttribute("role");
        Command command = null;
        if (requestType.equals(GET)) {
            command = GETCommandProvider.getInstance().getCommand(commandType);
        } else if (requestType.equals(POST)) {
            command = POSTCommandProvider.getInstance().getCommand(commandType);
        }
        if (command != null && RightsHolder.getInstance().rightsCorrect(
                commandType, role)) {
            command.execute(req, resp);
            logger.info("command executed");
        } else {
            logger.warn("can't get command, so uploading error page");
            req.getRequestDispatcher("error.html").forward(req, resp);
        }
    }
}
