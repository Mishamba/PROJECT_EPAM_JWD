package com.mishamba.project.service;

import com.mishamba.project.model.User;
import com.mishamba.project.exception.CustomServiceException;

import java.util.Date;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(int userId) throws CustomServiceException;
    Optional<User> getUserByEmail(String email) throws CustomServiceException;
    boolean createUser(String firstName, String lastName, String email,
                       String role, Date birthday, String password)
            throws CustomServiceException;
    boolean checkSignInData(String email, String password)
            throws CustomServiceException;
}
