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
    private final String GET_COURSE_BY_TEACHERS_ID = "SELECT courses.id, courses.course_name, " +
            "courses.begin_of_course, courses.end_of_course, courses.course_teacher, " +
            "courses.max_students_quantity, courses.finished, users.id, " +
            "users.first_name, users.last_name, users.birthday, users.email " +
            "FROM courses " +
            "LEFT JOIN users " +
            "ON courses.course_teacher = users.id " +
            "WHERE course_teacher=? AND finished=?";
    private final String STUDENT_ACTIVE_PASSED_COURSES = "SELECT courses.id, " +
            "courses.course_name, courses.begin_of_course, " +
            "courses.end_of_course, courses.course_teacher, " +
            "courses.max_students_quantity, courses.finished, " +
            "users.id as teacher_id, users.first_name, users.last_name, " +
            "users.birthday, users.email " +
            "FROM courses " +
            "LEFT JOIN " +
            "student_course_references " +
            "ON courses.id=student_course_references.course_id AND " +
            "student_course_references.student_id=? " +
            "LEFT JOIN users " +
            "ON courses.course_teacher = users.id " +
            "WHERE finished=?";
    private final String CHECK_STUDENT_COURSE_REFERENCES = "SELECT EXISTS (" +
            "SELECT student_id, course_id " +
            "FROM student_course_references " +
            "WHERE student_id = ? AND course_id = ?) AS exists_value";
    private final String ENTER_STUDENT_ON_COURSE = "INSERT INTO " +
            "student_course_references (student_id, course_id) " +
            "VALUES (?, ?)";
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

    @Override
    public boolean enterStudentOnCourse(int studentId, int courseId) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(CHECK_STUDENT_COURSE_REFERENCES);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt("exists_value") == 1) {
                return false;
            }
        } catch (SQLException e) {
            logger.error("can't check is there any such a student");
            throw new DAOException("can't check is there already " +
                    "such a student on course", e);
        }

        try {
            statement = connection.prepareStatement(ENTER_STUDENT_ON_COURSE);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();
        } catch (SQLException e) {
            logger.error("can't enter student on course");
            throw new DAOException("can't enter student on course", e);
        }

        return true;
    }

    @Override
    public ArrayList<Course> getStudentsCourses(int userId, boolean passed) throws DAOException {
        ArrayList<Course> courses = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(STUDENT_ACTIVE_PASSED_COURSES);
            statement.setInt(1, userId);
            statement.setBoolean(2, passed);
        } catch (SQLException e) {
            logger.error("can't form queue");
            throw new DAOException("can't form queue", e);
        }

        try {
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                courses.add(formCourseFromResultSet(resultSet));
            }
        } catch (SQLException | UtilException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue", e);
        }

        return courses;
    }

    @Override
    public Course getTeachersCourse(int teacherId) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(GET_COURSE_BY_TEACHERS_ID);
            statement.setInt(1, teacherId);
            statement.setBoolean(2, false);
        } catch (SQLException throwables) {
            logger.error("can't set queue parameters");
            throw new DAOException("can't set queue parameters");
        }

        DateParser dateParser = new DateParser();

        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            int courseId = resultSet.getInt("id");
            String courseName = resultSet.getString("course_name");
            Date beginOfCourse = dateParser.parseDate(resultSet.
                    getString("begin_of_course"));
            Date endOfCourse = dateParser.parseDate(resultSet.
                    getString("end_of_course"));
            int maxStudentsQuantity = resultSet.getInt("max_students_quantity");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date birthday = dateParser.parseDate(resultSet.getString("birthday"));
            String email = resultSet.getString("email");
            User teacher = new User(teacherId, firstName, lastName, email, birthday, "teacher");
            Course course = new Course(courseId, courseName, beginOfCourse,
                    endOfCourse, teacher, maxStudentsQuantity, null, false);

            ArrayList<ProgramStep> courseProgram = DAOFactory.getInstance().
                    getProgramStepDAO().getCourseProgram(course);

            course.setCourseProgram(courseProgram);

            return course;
        } catch (SQLException | UtilException e) {
            logger.error("can't execute queue");
            throw new DAOException("cant execute queue", e);
        }
    }
}