package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;

import java.util.ArrayList;

public interface CourseDAO {
    ArrayList<Course> getCoursesWithoutTeacher() throws DAOException;
    ArrayList<Course> getActiveCourses() throws DAOException;
    Integer getStudentsOnCourseQuantity(Course course) throws DAOException;
    Course getCourseById(int id) throws DAOException;
    void enterStudentOnCourse(int studentId, int courseId)
            throws DAOException;
    ArrayList<Course> getStudentsCourses(int studentId, boolean passed)
            throws DAOException;
    Course getTeacherManageCourse(int teacherId) throws DAOException;
    ArrayList<Course> getTeacherManagedCourses(int teacherId) throws DAOException;
}
