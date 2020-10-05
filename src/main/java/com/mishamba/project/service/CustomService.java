package com.mishamba.project.service;

import com.mishamba.project.service.exception.ServiceException;

import java.util.Properties;

public interface CustomService {
    String formMainCourses() throws ServiceException;
    String formCoursesCatalog() throws ServiceException;
    String formPageParameter(Properties properties) throws ServiceException;
    boolean checkSingInData(String email, String password) throws ServiceException;
    Properties getUserByEmail(String email) throws ServiceException;
    boolean createUser(Properties userInfo) throws ServiceException;
}
