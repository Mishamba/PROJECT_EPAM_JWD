package com.mishamba.project.service;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.User;
import com.mishamba.project.service.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.CheckedOutputStream;

public interface CustomService {
    boolean setMarkReview(Course course, User student, User teacher, int mark, String review,
                          boolean finishedParameter,
                          boolean gotCretificateParameter)
            throws CustomServiceException;
    String formPageParameter(Properties properties) throws CustomServiceException;
}
