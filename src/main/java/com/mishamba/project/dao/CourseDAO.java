package com.mishamba.project.dao;

import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.Course;

import java.util.ArrayList;

public interface CourseDAO {
    boolean appendTeacherOnCourse(int courseId, int teacherId) throws DAOException;
    ArrayList<Course> getCoursesWithoutTeacher() throws DAOException;
    ArrayList<Course> getActiveCourses() throws DAOException;
    Course getCourseById(int id) throws DAOException;
    boolean enterStudentOnCourse(int studentId, int courseId)
            throws DAOException;
    ArrayList<Course> getStudentsCourses(int studentId, boolean passed)
            throws DAOException;
    Course getTeacherManageCourse(int teacherId) throws DAOException;
    ArrayList<Course> getTeacherManagedCourses(int teacherId) throws DAOException;
    boolean kickStudentFromCourse(int studentId, int courseId) throws DAOException;
}
