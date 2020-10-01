package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.DAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.*;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DAOImpl implements DAO {
    private final Logger logger = Logger.getRootLogger();
    private final String TEACHER_BY_FIRST_LAST_NAME_QUEUE = "SELECT id, first_name, last_name, email, birthday " +
            "FROM users " +
            "WHERE role = 'teacher' AND first_name = ? AND last_name = ?";
    private final String MARK_REVIEW_QUEUE = "SELECT " +
            "users.id as teacher_id" +
            "users.first_name AS teacher_first_name," +
            "users.last_name as teacher_last_name, finished, " +
            "users.id as teacher_id, user.email as teacher_email, " +
            "user.birthday as teacher_birthday, " +
            "mark, finished_date, review, got_certificate " +
            "FROM student_mark_review " +
            "LEFT JOIN " +
            "users " +
            "ON student_mark_review.teacher_id = users.id " +
            "WHERE student_mark_review.student_id = ? AND student_mark_review.course_id = ?";
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
            "SELECT COUNT(student_id) AS quantity" +
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwable) {
                logger.warn("result set is null");
                throwable.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException throwable) {
                logger.warn("statement is null");
                throwable.printStackTrace();
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return users;
    }

    @Override
    public ArrayList<User> getTeacherByFirstNameLastName(String firstName, String lastName) throws DAOException {
        ArrayList<User> teachers = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(TEACHER_BY_FIRST_LAST_NAME_QUEUE);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date birthday = resultSet.getDate("birthday");
                teachers.add(new User(id, firstName, lastName, email, birthday,
                        "teacher"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return teachers;
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwable) {
                logger.warn("can't close resultSet because it's NULL");
            }
            try {
                statement.close();
            } catch (SQLException throwable) {
                logger.warn("can't close statement because it's NULL");
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
        if (course == null || student == null) {
            throw new DAOException("course of student is null");
        }

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<MarkReview> markReviews = new ArrayList<>();
        try {
            connection = ConnectionPoolImpl.getInstance().
                    getConnection();
            statement = connection.prepareStatement(MARK_REVIEW_QUEUE);
            statement.setInt(1, student.getId());
            statement.setInt(2, student.getId());
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Integer teacher_id = resultSet.getInt("teacher_id");
                String teacherFirstName = resultSet.getString("teacher_first_name");
                String teacherLastName = resultSet.getString("teacher_last_name");
                Date teacherBirthday = resultSet.getDate("teacher_birthday");
                String teacherEmail = resultSet.getString("teacher_email");
                User teacher = new User(teacher_id, teacherFirstName,
                        teacherLastName, teacherEmail, teacherBirthday,
                        "teacher");
                Boolean finished = resultSet.getBoolean("finished");
                Integer mark = resultSet.getInt("mark");
                Date finishDate = resultSet.getDate("finish_date");
                String review = resultSet.getString("review");
                Boolean gotCertificate = resultSet.getBoolean("got_certificate");
                markReviews.add(new MarkReview(student, teacher, course,
                        finished, mark, finishDate, review, gotCertificate));
            }
        } catch (SQLException throwable) {
            throw new DAOException("can't get mark review", throwable);
        }

        return markReviews;
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
    public Integer getStudentsOnCourseQuantity(Course course) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(STUDENTS_ON_COURSE_QUANTITY_QUEUE);
            statement.setInt(1, course.getId());
            resultSet = statement.executeQuery();
            return resultSet.getInt("quantity");
        } catch (SQLException throwables) {
            throw new DAOException("can't get students quantity " +
                    "on course with name" + course.getCourseName(), throwables);
        }
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
