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
import java.util.Date;
import java.util.Optional;

public class SignUpProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SignUpProcessCommand.class);
    private final String FIRST_NAME_PARAMETER = "first_name";
    private final String LAST_NAME_PARAMETER = "last_name";
    private final String EMAIL_PARAMETER = "email";
    private final String ROLE_PARAMETER = "role";
    private final String BIRTHDAY_PARAMETER = "birthday";
    private final String PASSWORD_PARAMETER = "password";
    private final String USER_PARAMETER = "user";
    private final String ADMIN = "admin";
    private final String STUDENT = "student";
    private final String ANONYM = "anonym";
    private final String INDEX_PAGE = "index.jsp";
    private final String ERROR_PAGE = "error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = (String) request.getAttribute(FIRST_NAME_PARAMETER);
        String lastName = (String) request.getAttribute(LAST_NAME_PARAMETER);
        String email = (String) request.getAttribute(EMAIL_PARAMETER);
        // admin can create user with any role,
        // but anonym can sign up only as student
        String sessionRole = (String) request.getSession().getAttribute(ROLE_PARAMETER);
        if (sessionRole == null) {
            sessionRole = ANONYM;
        }

        String role = (sessionRole.equals(ADMIN)) ?
                (String) request.getAttribute(ROLE_PARAMETER) : STUDENT;
        Date birthday = (Date) request.getAttribute(BIRTHDAY_PARAMETER);
        String password = (String) request.getAttribute(PASSWORD_PARAMETER);

        String uploadPage = INDEX_PAGE;

        try {
            if (ServiceFactory.getInstance().getUserService().
                    createUser(firstName, lastName, email, role,
                            birthday, password)) {
                uploadPage = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't create user");
            uploadPage = ERROR_PAGE;
        }

        User user = null;
        try {
            Optional<User> optionalUser;
            optionalUser = ServiceFactory.getInstance().getUserService().getUserByEmail(email);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
        } catch (CustomServiceException e) {
            logger.error("can't get user info");
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(USER_PARAMETER, user);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload page");
        }
    }
}
