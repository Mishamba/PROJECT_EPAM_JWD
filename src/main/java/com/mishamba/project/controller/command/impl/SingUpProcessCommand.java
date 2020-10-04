package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

public class SingUpProcessCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getting sing in info");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthday = request.getParameter("birthday");

        Properties singUpInfo = new Properties();
        singUpInfo.setProperty("firstName", firstName);
        singUpInfo.setProperty("lastName", lastName);
        singUpInfo.setProperty("email", email);
        singUpInfo.setProperty("password", password);
        singUpInfo.setProperty("birthday", birthday);

    }
}
