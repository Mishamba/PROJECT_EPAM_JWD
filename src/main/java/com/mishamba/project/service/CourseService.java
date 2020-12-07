package com.mishamba.project.service;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.exception.CustomServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    boolean appendTeacherOnCoruse(int courseId, int teacherId) throws CustomServiceException;
    ArrayList<Course> getCoursesWithoutTeacher() throws CustomServiceException;
    ArrayList<Course> getActiveCourses() throws CustomServiceException;
    List<Course> getUserCourses(int userId, String role, boolean finished) throws CustomServiceException;
    boolean isStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    boolean enterStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException;
    Optional<Course> getCourseById(int courseId) throws CustomServiceException;
    ArrayList<User> getStudentsOnCourse(int courseId) throws CustomServiceException;
}
