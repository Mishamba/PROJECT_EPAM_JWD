package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.*;

import java.util.ArrayList;

public interface DAO {
    ArrayList<User> getStudentByFirstNameLastName(
            String firstName, String lastName) throws DAOException;
    ArrayList<User> getTeacherByFirstNameLastName(
            String firstName, String lastName) throws DAOException;
    ArrayList<Course> getCoursesByName(String courseName) throws DAOException;
    ArrayList<Course> getCoursesWithoutTeacher() throws DAOException;
    ArrayList<Course> getActiveCourses() throws DAOException;
    ArrayList<Hometask> getHometaskForCourse(Course course)
            throws DAOException;
    ArrayList<MarkReview> getMarkReview(Course course, User student)
            throws DAOException;
    ArrayList<ProgramStep> getCourseProgram(Course course) throws DAOException;
    boolean createUser(User user) throws DAOException;
    boolean createCourse(Course course) throws DAOException;
    boolean createHometask(Hometask hometask) throws DAOException;
    boolean createMarkReview(MarkReview markReview) throws DAOException;
    boolean authorize(AuthorizationData data) throws DAOException;
}
