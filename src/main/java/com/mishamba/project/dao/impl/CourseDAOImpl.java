package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.CourseDAO;
import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.model.User;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.DateParser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CourseDAOImpl implements CourseDAO {
    private final Logger logger = Logger.getRootLogger();
    private final String COURSES_WITHOUT_TEACHER_QUEUE =
            "SELECT id, course_name, begin_of_course, " +
                    "end_of_course, max_students_quantity, finished " +
                    "FROM courses " +
                    "WHERE finished = FALSE AND course_teacher IS NULL";
    private final String ACTIVE_COURSES = "SELECT courses.id, courses.course_name, " +
            "courses.begin_of_course, courses.end_of_course, " +
            "courses.max_students_quantity, courses.finished, " +
            "users.first_name, users.last_name, users.birthday " +
            "FROM courses " +
            "LEFT JOIN " +
            "users " +
            "on courses.course_teacher = users.id";
    private final String STUDENTS_ON_COURSE_QUANTITY_QUEUE =
            "SELECT COUNT(student_id) AS quantity " +
                    "FROM student_course_references " +
                    "WHERE course_id = ?";
    private final String GET_COURSE_BY_ID = "SELECT courses.id, " +
            "courses.course_name, courses.begin_of_course, " +
            "courses.end_of_course, courses.max_students_quantity, " +
            "courses.finished, users.first_name, users.last_name, " +
            "users.birthday " +
            "FROM courses " +
            "LEFT JOIN " +
            "users " +
            "on courses.course_teacher = users.id " +
            "WHERE courses.id = ?";

    @Override
    public ArrayList<Course> getCoursesWithoutTeacher() throws DAOException {
        ArrayList<Course> courses = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().
                getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        DateParser dateParser = new DateParser();

        try {
            statement = connection.prepareStatement(COURSES_WITHOUT_TEACHER_QUEUE);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");
                Date beginOfCourse = dateParser.parseDate(resultSet.getString("begin_of_course"));
                Date endOfCourse = dateParser.parseDate(resultSet.getString("end_of_course"));
                Integer maxStudentQuantity = resultSet.getInt("max_students_quantity");
                courses.add(new Course(id, courseName, beginOfCourse, endOfCourse,
                        null, maxStudentQuantity, null, false));
            }
        } catch (SQLException | UtilException throwable) {
            throw new DAOException("can't get courses", throwable);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwable) {
                logger.error("can't close resultSet");
                throwable.printStackTrace();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwable) {
                logger.error("can't close statement");
                throwable.printStackTrace();
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return courses;
    }

    @Override
    public ArrayList<Course> getActiveCourses() throws DAOException {
        ArrayList<Course> courses = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(ACTIVE_COURSES);
            while (resultSet.next()) {
                courses.add(formCourseFromResultSet(resultSet));
            }
        } catch (SQLException | UtilException throwable) {
            throw new DAOException("can't get active courses", throwable);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwable) {
                logger.warn("can't close resultSet because it's NULL");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwable) {
                logger.warn("can't close statement because it's NULL");
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return courses;
    }

    @Override
    public Integer getStudentsOnCourseQuantity(Course course) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(STUDENTS_ON_COURSE_QUANTITY_QUEUE);
            statement.setInt(1, course.getId());
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("quantity");
        } catch (SQLException throwables) {
            throw new DAOException("can't get students quantity " +
                    "on course with name" + course.getCourseName(), throwables);
        }
    }

    private Course formCourseFromResultSet(ResultSet resultSet) throws SQLException, DAOException, UtilException {
        DateParser dateParser = new DateParser();

        Integer courseId = resultSet.getInt("id");
        String courseName = resultSet.getString("course_name");
        Date beginOfCourse = dateParser.parseDate(resultSet.getString("begin_of_course"));
        Date endOfCourse = dateParser.parseDate(resultSet.getString("end_of_course"));
        String teacherFirstName = resultSet.getString("first_name");
        String teacherLastName = resultSet.getString("last_name");
        Date teacherBirthday = dateParser.parseDate(resultSet.getString("birthday"));
        User teacher = null;
        if (teacherFirstName != null || teacherLastName != null) {
            teacher = new User(null, teacherFirstName, teacherLastName,
                    null, teacherBirthday, "teacher");
        }
        Integer maxStudentsQuantity = resultSet.getInt("max_students_quantity");
        Boolean finished = resultSet.getBoolean("finished");
        Course course = new Course(courseId, courseName, beginOfCourse, endOfCourse,
                teacher, maxStudentsQuantity, null, finished);
        ArrayList<ProgramStep> courseProgram = DAOFactory.getInstance().
                getProgramStepDAO().getCourseProgram(course);
        course.setCourseProgram(courseProgram);

        return course;
    }

    @Override
    public Course getCourseById(int id) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(GET_COURSE_BY_ID);
            statement.setInt(1, id);
        } catch (SQLException e) {
            logger.error("can't create queue");
            throw new DAOException("can't create queue", e);
        }

        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return formCourseFromResultSet(resultSet);
        } catch (SQLException | UtilException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue", e);
        }
    }
}
