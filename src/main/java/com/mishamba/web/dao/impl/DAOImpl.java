package com.mishamba.web.dao.impl;

import com.mishamba.web.dao.DAO;
import com.mishamba.web.dao.ProxyConnection;
import com.mishamba.web.dao.exception.DAOException;
import com.mishamba.web.model.*;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DAOImpl implements DAO {
    private final Logger logger = Logger.getRootLogger();
    private final String STUDENT_QUEUE =
            "SELECT id, first_name, last_name, email, birthday " +
            "FROM users WHERE role = 'student' " +
                    "AND first_name = ? " +
                    "AND last_name = ?";
    private final String COURSES_WITHOUT_TEACHER_QUEUE =
            "SELECT id, course_name, begin_of_course, " +
                    "end_of_course, max_students_quantity, finished " +
            "FROM courses " +
            "WHERE finished = FALSE AND course_teacher IS NULL";
    private final String STUDENTS_ON_COURSE_QUANTITY_QUEUE =
            "SELECT COUNT(student_id)" +
            "FROM student_course_references" +
            "WHERE course_id = ?";
    private final String COURSE_PROGRAM_QUEUE =
            "SELECT step, step_name, step_description, start_date, end_date " +
                    "FROM course_programs " +
                    "WHERE course_id = ?";
    private final String ACTIVE_COURSES = "SELECT courses.id, courses.course_name, " +
            "courses.begin_of_course, courses.end_of_course, " +
            "courses.max_students_quantity, courses.finished, " +
            "users.first_name, users.last_name, users.birthday " +
            "FROM courses " +
            "LEFT JOIN " +
            "users" +
            "on courses.course_teacher = users.id";

    private DAOImpl() {}

    private static class DAOImplHolder {
        private static final DAOImpl HOLDER = new DAOImpl();
    }

    public static DAOImpl getInstance() {
        return DAOImplHolder.HOLDER;
    }

    @Override
    public ArrayList<User> getStudentByFirstNameLastName(
            String firstName, String lastName) throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().
                getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(STUDENT_QUEUE);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date birthday = resultSet.getDate("birthday");
                users.add(new User(id, firstName, lastName, email, birthday,
                        "student"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                logger.warn("result set is null");
                throwables.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException throwables) {
                logger.warn("statement is null");
                throwables.printStackTrace();
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return users;
    }

    @Override
    public ArrayList<User> getTeacherByFirstNameLastName(String firstName, String lastName) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<Course> getCoursesByName(String courseName) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<Course> getCoursesWithoutTeacher() throws DAOException {
        ArrayList<Course> courses = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().
                getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(COURSES_WITHOUT_TEACHER_QUEUE);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");
                Date beginOfCourse = resultSet.getDate("begin_of_course");
                Date endOfCourse = resultSet.getDate("end_of_course");
                Integer maxStudentQuantity = resultSet.getInt("max_students_quantity");
                courses.add(new Course(id, courseName, beginOfCourse, endOfCourse,
                        null, maxStudentQuantity, null, false));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
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
                Integer id = resultSet.getInt("id");
                String courseName = resultSet.getString("course_name");
                Date beginOfCourse = resultSet.getDate("begin_of_course");
                Date endOfCourse = resultSet.getDate("end_of_course");
                Integer maxStudentQuantity = resultSet.getInt("max_student_quantity");
                String teacherFirstName = resultSet.getString("first_name");
                String teacherLastName = resultSet.getString("last_name");
                Date birthday = resultSet.getDate("birthday");
                User teacher = null;
                if (teacherFirstName != null || teacherLastName != null) {
                    teacher = new User(null, teacherFirstName, teacherLastName,
                            null, birthday, "teacher");
                }
                courses.add(new Course(id, courseName, beginOfCourse,
                        endOfCourse, teacher, maxStudentQuantity, null, false));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return courses;
    }

    @Override
    public ArrayList<Hometask> getHometaskForCourse(Course course) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<MarkReview> getMarkReview(Course course, User student) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<ProgramStep> getCourseProgram(Course course) throws DAOException {
        if (course == null) {
            throw new DAOException("no course given");
        }
        ArrayList<ProgramStep> coursePrograms = new ArrayList<>();
        try {
            ProxyConnection connection = ConnectionPoolImpl.getInstance().
                    getConnection();
            PreparedStatement statement = connection.
                    prepareStatement(COURSE_PROGRAM_QUEUE);
            statement.setInt(1, course.getId());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int step = resultSet.getInt("step");
                String stepName = resultSet.getString("step_name");
                String description = resultSet.getString("step_description");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                coursePrograms.add(new ProgramStep(course.getId(),
                        step, stepName, description, startDate, endDate));
            }
        } catch (DAOException | SQLException e) {
            e.printStackTrace();
        }

        return coursePrograms;
    }

    @Override
    public boolean createUser(User user) throws DAOException {
        return false;
    }

    @Override
    public boolean createCourse(Course course) throws DAOException {
        return false;
    }

    @Override
    public boolean createHometask(Hometask hometask) throws DAOException {
        return false;
    }

    @Override
    public boolean createMarkReview(MarkReview markReview) throws DAOException {
        return false;
    }

    @Override
    public boolean authorize(AuthorizationData data) throws DAOException {
        return false;
    }
}
