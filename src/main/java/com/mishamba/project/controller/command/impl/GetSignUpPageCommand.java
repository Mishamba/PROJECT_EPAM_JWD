package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetSignUpPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetSignUpPageCommand.class);

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse response) {
        Properties properties = formProperties(req);
        String roleDefiner;
        try {
            roleDefiner = ServiceFactory.getInstance().
                    getCustomService().formPageParameter(properties);
        } catch (CustomServiceException e) {
            logger.error("can't form role definer");
            roleDefiner = "error happened. your role is student.";
        }

        req.setAttribute("role_definer", roleDefiner);

        try {
            logger.info("uploading sign up page");
            req.getRequestDispatcher("sign_up.jsp").
                    forward(req, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload sign up page");
        }
    }

    private Properties formProperties(HttpServletRequest req) {
        logger.info("getting user role info");
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }
        Properties userProp = new Properties();
        userProp.setProperty("role", role);
        userProp.setProperty("target", "sign up");

        return userProp;
    }
}
