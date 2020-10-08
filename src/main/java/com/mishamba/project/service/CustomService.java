package com.mishamba.project.service;

import com.mishamba.project.service.exception.ServiceException;

import java.util.Properties;

public interface CustomService {
    String formMainCourses() throws ServiceException;
    String formCoursesCatalog() throws ServiceException;
    String formPageParameter(Properties properties) throws ServiceException;
    boolean checkSignInData(String email, String password)
            throws ServiceException;
    Properties getUserByEmail(String email) throws ServiceException;
    boolean createUser(Properties userInfo) throws ServiceException;
    String formCourseProfile(int courseId) throws ServiceException;
    boolean enterStudentOnCourse(int studentId, int courseId)
            throws ServiceException;
    int getUserIdByEmail(String email) throws ServiceException;
    String formUserCourses(Properties properties) throws ServiceException;
}
