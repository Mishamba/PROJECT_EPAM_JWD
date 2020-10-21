package com.mishamba.project.service;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.exception.CustomServiceException;

import java.util.Properties;

public interface CustomService {
    User getUserById(int userId) throws CustomServiceException;
    boolean setMarkReview(Course course, User student, User teacher, int mark, String review,
                          boolean finishedParameter,
                          boolean gotCretificateParameter)
            throws CustomServiceException;
    String getCourseHometaskForUser(int courseId, int studentId, String role)
            throws CustomServiceException;
    String getMainCourses() throws CustomServiceException;
    String getCoursesCatalog() throws CustomServiceException;
    String formPageParameter(Properties properties) throws CustomServiceException;
    boolean checkSignInData(String email, String password)
            throws CustomServiceException;
    Properties getUserByEmail(String email) throws CustomServiceException;
    boolean createUser(Properties userInfo) throws CustomServiceException;
    String getCourseProfile(int courseId) throws CustomServiceException;
    void enterStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    int getUserIdByEmail(String email) throws CustomServiceException;
    String getUserCourses(Properties properties) throws CustomServiceException;
    boolean isStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    boolean isTeacherLeadsOrLeadedCourse(int teacherId, int courseId)
            throws CustomServiceException;
    Properties getCourseById(int courseId) throws CustomServiceException;
    String getStudentsOnCourse(int courseId) throws CustomServiceException;
    void createHometask(Properties hometaskProperties) throws CustomServiceException;
    String getHometaskById(int hometaskId) throws CustomServiceException;
    boolean writeHometaskAnswer(String answer, int hometaskId, int studentId)
            throws CustomServiceException;
    String getUserInfoById(int userId) throws CustomServiceException;
    String getStudentHometaskWithResponce(int hometaskId, int studentId)
            throws CustomServiceException;
    boolean setHometaskMark(int hometaskId, int studentId, int mark)
            throws  CustomServiceException;
}
