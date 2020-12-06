package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.User;
import com.mishamba.project.service.UserService;
import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.util.validator.DateValidator;
import com.mishamba.project.util.validator.EmailValidator;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> getUserById(int userId) throws CustomServiceException {
        if (userId == 0) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(DAOFactory.getInstance().getUserDAO().getUserById(userId));
        } catch (DAOException e) {
            logger.error("can't get user");
            throw new CustomServiceException("can't get user by id", e);
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws CustomServiceException {
        try {
            User user = DAOFactory.getInstance().getUserDAO().
                    getUserByEmail(email);
            return Optional.ofNullable(user);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get first name", e);
        }
    }

    @Override
    public boolean createUser(String firstName, String lastName, String email,
                              String role, Date birthday, String password)
            throws CustomServiceException {
        int passwordHash = password.hashCode();
        passwordHash = Integer.valueOf(passwordHash).hashCode();

        DateValidator dateValidator = new DateValidator();
        EmailValidator emailValidator = new EmailValidator();
        if (!dateValidator.checkForFuture(birthday) &&
                emailValidator.valid(email)) {
            User newUser = new User(null, firstName, lastName,
                    email, birthday, null, role);
            try {
                return DAOFactory.getInstance().getUserDAO().createUser(newUser, passwordHash);
            } catch (DAOException e) {
                throw new CustomServiceException("can't sign up new user", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean checkSignInData(String email, String password) throws CustomServiceException {
        int passwordHash = password.hashCode();
        passwordHash = Integer.valueOf(passwordHash).hashCode();

        try {
            return DAOFactory.getInstance().getUserDAO().
                    checkSignInData(email, passwordHash);
        } catch (DAOException e) {
            throw new CustomServiceException("can't check sign in data", e);
        }
    }
}
