package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.CustomServiceFactory;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetSignUpPageCommand implements Command {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response) {
        logger.info("getting user role info");
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role == null) {
            role = "anonym";
        }
        Properties definer = new Properties();
        definer.setProperty("role", role);
        definer.setProperty("target", "sign up");
        String roleDefiner;
        try {
            roleDefiner = CustomServiceFactory.getInstance().
                    getCustomService().formPageParameter(definer);
        } catch (ServiceException e) {
            logger.error("can't form role definer");
            roleDefiner = "error happened. your role is student.";
        }

        request.setAttribute("role_definer", roleDefiner);

        try {
            logger.info("uploading sign up page");
            request.getRequestDispatcher("sign_up.jsp").
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload sign up page");
        }
    }
}