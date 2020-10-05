package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class GetSingUpPageCommand implements Command {
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
        definer.setProperty("page", "sing up");
        definer.setProperty("target", "field");
        String roleDefiner;
        try {
            roleDefiner = CustomServiceImpl.getInstance().
                    formPageParameter(definer);
        } catch (ServiceException e) {
            logger.error("can't form role definer");
            roleDefiner = "error happened. your role is student.";
        }

        request.setAttribute("role_definer", roleDefiner);

        try {
            logger.info("uploading sing up page");
            request.getRequestDispatcher("sing_up.jsp").
                    forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload sing up page");
        }
    }
}
