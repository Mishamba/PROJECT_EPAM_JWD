package com.mishamba.project.service;

import com.mishamba.project.model.User;
import com.mishamba.project.service.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public interface UserService {
    Optional<User> getUserById(int userId) throws CustomServiceException;
    Optional<User> getUserByEmail(String email) throws CustomServiceException;
    boolean createUser(Properties userInfo) throws CustomServiceException;
    boolean checkSignInData(String email, String password)
            throws CustomServiceException;
}
