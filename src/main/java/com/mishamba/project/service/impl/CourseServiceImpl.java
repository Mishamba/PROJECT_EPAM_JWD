package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.CourseService;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import java.util.*;

public class CourseServiceImpl implements CourseService {
    private static final Logger logger = Logger.getLogger(CourseServiceImpl.class);

    @Override
    public ArrayList<Course> getCoursesWithoutTeacher() throws CustomServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOFactory.getInstance().getCourseDAO().getCoursesWithoutTeacher();
        } catch (DAOException e) {
            throw new CustomServiceException("can't get courses without teacher");
        }

        if (courses == null) {
            courses = new ArrayList<>();
        }

        return courses;
    }

    @Override
    public ArrayList<Course> getCoursesCatalog() throws CustomServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOFactory.getInstance().getCourseDAO().getActiveCourses();
        } catch (DAOException e) {
            throw new CustomServiceException("can't get active courses", e);
        }

        if (courses == null) {
            courses = new ArrayList<>();
        }

        return courses;
    }

    @Override
    public List<Course> getUserCourses(int userId, String role, boolean finished) throws CustomServiceException {
        List<Course> courses;
        try {
            courses = (role.equals("student")) ? DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(userId, finished) :
                    (finished) ? DAOFactory.getInstance().getCourseDAO().
                            getTeacherManagedCourses(userId) :
                            Collections.singletonList(DAOFactory.getInstance().
                                    getCourseDAO().getTeacherManageCourse(userId));
            if (courses == null) {
                courses = new ArrayList<>();
            }

            return courses;
        } catch (DAOException e) {
            throw new CustomServiceException("can't get courses", e);
        }
    }

    @Override
    public boolean isStudentOnCourse(int studentId, int courseId) throws CustomServiceException {
        try {
            ArrayList<Course> usersCourses = DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(studentId, false);
            ArrayList<Course> usersPassedCourse = DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(studentId, true);
            usersCourses.addAll(usersPassedCourse);
            for (Course course : usersCourses) {
                if (course.getId() == courseId) {
                    return true;
                }
            }
        } catch (DAOException e) {
            throw new CustomServiceException("can't check is student on course", e);
        }

        return false;
    }

    @Override
    public boolean enterStudentOnCourse(int studentId, int courseId) throws CustomServiceException {
        try {
            return DAOFactory.getInstance().getCourseDAO().
                    enterStudentOnCourse(studentId, courseId);
        } catch (DAOException e) {
            logger.error("can't enter student on course");
            throw new CustomServiceException("can't enter student on course", e);
        }
    }

    @Override
    public Optional<Course> getCourseById(int courseId) throws CustomServiceException {
        try {
            return Optional.ofNullable(DAOFactory.getInstance().getCourseDAO().getCourseById(courseId));
        } catch (DAOException e) {
            logger.error("can't get course");
            throw new CustomServiceException("can't get course", e);
        }
    }

    @Override
    public ArrayList<User> getStudentsOnCourse(int courseId) throws CustomServiceException {
        try {
            ArrayList<User> students = DAOFactory.getInstance().getUserDAO().
                    getStudentsOnCourse(courseId);

            if (students == null) {
                students = new ArrayList<>();
            }

            return students;
        } catch (DAOException e) {
            logger.error("can't get students on course");
            throw new CustomServiceException("can't get students on course");
        }
    }
}
