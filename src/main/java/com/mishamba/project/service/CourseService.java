package com.mishamba.project.service;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface CourseService {
    ArrayList<Course> getCoursesWithoutTeacher() throws CustomServiceException;
    ArrayList<Course> getCoursesCatalog() throws CustomServiceException;
    List<Course> getUserCourses(Properties properties) throws CustomServiceException;
    boolean isStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    boolean enterStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    Optional<Course> getCourseById(int courseId) throws CustomServiceException;
    ArrayList<User> getStudentsOnCourse(int courseId) throws CustomServiceException;
}
