package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GetUserProfilePageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetUserProfilePageCommand.class);
    private final String USER_PROFILE_PAGE = "user_profile.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String ID = "id";
    private final String USER_PARAMETER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = USER_PROFILE_PAGE;

        int userId = (int) request.getSession().getAttribute(ID);

        User user = null;
        try {
            Optional<User> optionalUser;
            optionalUser = ServiceFactory.getInstance().getUserService().getUserById(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
        } catch (CustomServiceException | NoSuchElementException e) {
            logger.warn("user without role tries to get profile page");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(USER_PARAMETER, user);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send profile page");
        }
    }
}
