package com.mishamba.project.service;

import com.mishamba.project.service.exception.ServiceException;

public interface CustomService {
    String formMainCourses() throws ServiceException;
    String formCoursesCatalog() throws ServiceException;
}
