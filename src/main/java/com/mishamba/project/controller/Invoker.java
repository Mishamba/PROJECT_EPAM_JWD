package com.mishamba.project.controller;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.factory.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Invoker extends HttpServlet {
    private final Logger logger = Logger.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("got connection");

        String commandType = req.getParameter("command");
        Command command = CommandProvider.getInstance().
                getCommand(commandType);
        command.execute(req, resp);
    }
}
