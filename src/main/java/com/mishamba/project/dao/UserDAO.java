package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.User;

import java.util.ArrayList;

public interface UserDAO {
    boolean checkSingInData(String email, int password) throws DAOException;
    User getUserByEmail(String email) throws DAOException;
    boolean createUser(User user, int password) throws DAOException;
    ArrayList<User> getTeacherByFirstNameLastName(
            String firstName, String lastName) throws DAOException;
    ArrayList<User> getStudentByFirstNameLastName(
            String firstName, String lastName) throws DAOException;
}
