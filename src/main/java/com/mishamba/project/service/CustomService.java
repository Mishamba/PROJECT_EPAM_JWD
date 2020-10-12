package com.mishamba.project.service;

import com.mishamba.project.service.exception.CustomServiceException;

import java.util.Properties;

public interface CustomService {
    String getCourseHometask(int courseId) throws CustomServiceException;
    String getMainCourses() throws CustomServiceException;
    String getCoursesCatalog() throws CustomServiceException;
    String formPageParameter(Properties properties) throws CustomServiceException;
    boolean checkSignInData(String email, String password)
            throws CustomServiceException;
    Properties getUserByEmail(String email) throws CustomServiceException;
    boolean createUser(Properties userInfo) throws CustomServiceException;
    String getCourseProfile(int courseId) throws CustomServiceException;
    boolean enterStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    int getUserIdByEmail(String email) throws CustomServiceException;
    String getUserCourses(Properties properties) throws CustomServiceException;
    boolean isStudentOnCourse(int studentId, int courseId) throws CustomServiceException;
    boolean isTeacherLeadsOrLeadedCourse(int teacherId, int courseId,
                                         boolean finished) throws CustomServiceException;
    Properties getCourseById(int courseId) throws CustomServiceException;
    String getStudentsOnCourse(int courseId) throws CustomServiceException;
}
