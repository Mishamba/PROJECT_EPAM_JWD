package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.UserDAO;
import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.User;
import com.mishamba.project.exception.UtilException;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserDAOImpl implements UserDAO {
    private final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private final String GET_USER_BY_ID = "SELECT id, first_name, last_name, email, " +
            "birthday, role " +
            "FROM users " +
            "WHERE id = ?";
    private final String GET_TEACHER_SUBJECTS_BY_TEACHER_ID = "SELECT subject_name " +
            "FROM teacher_subjects " +
            "WHERE teacher_id = ?";
    private final String GET_STUDENTS_ON_COURSE = "SELECT id, first_name, " +
            "last_name, birthday, email, role " +
            "FROM users " +
            "LEFT JOIN student_course_references " +
            "ON student_course_references.student_id=users.id " +
            "WHERE student_course_references.course_id=?";
    private final String STUDENT_ID_BY_EMAIL = "SELECT id " +
            "FROM users " +
            "WHERE email = ?";
    private final String STUDENT_QUEUE =
            "SELECT id, first_name, last_name, email, birthday " +
                    "FROM users WHERE role = 'student' " +
                    "AND first_name = ? " +
                    "AND last_name = ?";
    private final String TEACHER_BY_FIRST_LAST_NAME_QUEUE = "SELECT id, first_name, last_name, email, birthday " +
            "FROM users " +
            "WHERE role = 'teacher' AND first_name = ? AND last_name = ?";
    private final String CHECK_SIGN_IN_DATA = "SELECT EXISTS (" +
            "SELECT email, password_hash " +
            "FROM users " +
            "WHERE email = ? AND password_hash = ?) as exists_value";
    private final String GET_USER_BY_EMAIL = "SELECT id, first_name, last_name, " +
            "birthday, role " +
            "FROM users " +
            "WHERE email = ?";
    private final String CHECK_EMAIL_UNIQUE = "SELECT EXISTS " +
            "(SELECT  email FROM users WHERE email=?) as exists_value";
    private final String CREATE_USER = "INSERT INTO users " +
            "(first_name, last_name, email, password_hash, birthday, role) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public boolean checkSignInData(String givenEmail, int givenPasswordHash) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(CHECK_SIGN_IN_DATA);
        } catch (SQLException e) {
            throw new DAOException("can't create statement", e);
        }

        try {
            statement.setString(1, givenEmail);
            statement.setInt(2, givenPasswordHash);
        } catch (SQLException e) {
            throw new DAOException("can't set queue parameters", e);
        }

        try {
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("exists_value");
        } catch (SQLException e) {
            throw new DAOException("can't execute queue", e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(GET_USER_BY_EMAIL);
            statement.setString(1, email);
        } catch (SQLException e) {
            throw new DAOException("can't create statement", e);
        }

        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("can't execute queue", e);
        }

        DateParser dateParser = new DateParser();

        try {
            resultSet.next();
            Integer id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date birthday = dateParser.parse(resultSet.getString("birthday"));
            String role = resultSet.getString("role");
            ArrayList<String> teacherSubjects = getTeacherSubjects(id);
            return new User(id, firstName, lastName, email, birthday,teacherSubjects, role);
        } catch (SQLException | UtilException e) {
            throw new DAOException("can't get result from result set", e);
        }
    }

    @Override
    public User getUserById(int userId) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            resultSet.next();
            return formUser(resultSet);
        } catch (SQLException | UtilException e) {
            throw new DAOException("can't execute queue");
        }
    }

    @Override
    public boolean createUser(User user, int password) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(CHECK_EMAIL_UNIQUE);
            statement.setString(1, user.getEmail());
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getInt("exists_value") == 1) {
                logger.warn("can't create new user. " +
                        "such email already exists in database.");
                return false;
            }
            logger.info("email is unique. going to create user");
        } catch (SQLException e) {
            throw new DAOException("can't check email unique", e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                logger.warn("result set is null");
            }
        }
        DateParser dateParser = new DateParser();

        try {
            statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, password);
            statement.setString(5, dateParser.parseDateToString(user.getBirthday()));
            statement.setString(6, user.getRole());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("can't make a write in " +
                    "database with new user", e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("can't close result set");
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                logger.warn("can't close statement");
            }

            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return true;
    }

    @Override
    public ArrayList<User> getTeacherByFirstNameLastName(String firstName, String lastName) throws DAOException {
        ArrayList<User> teachers = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        DateParser dateParser = new DateParser();

        try {
            statement = connection.prepareStatement(TEACHER_BY_FIRST_LAST_NAME_QUEUE);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date birthday = dateParser.parse(resultSet.getString("birthday"));
                ArrayList<String> teacherSubjects = getTeacherSubjects(id);
                teachers.add(new User(id, firstName, lastName, email, birthday,
                        teacherSubjects, "teacher"));
            }
        } catch (SQLException | UtilException e) {
            logger.error("can't get teacher");
            throw new DAOException("can't get teacher", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (NullPointerException | SQLException e) {
                logger.error("can't close null result set");
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (NullPointerException | SQLException e) {
                logger.error("can't close null statement");
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return teachers;
    }

    @Override
    public ArrayList<User> getStudentByFirstNameLastName(
            String firstName, String lastName) throws DAOException {
        ArrayList<User> users = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().
                getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        DateParser dateParser = new DateParser();
        try {
            statement = connection.prepareStatement(STUDENT_QUEUE);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date birthday = dateParser.parse(resultSet.getString("birthday"));
                users.add(new User(id, firstName, lastName, email, birthday, null,
                        "student"));
            }
        } catch (SQLException | UtilException throwable) {
            throw new DAOException("can't get students", throwable);
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwable) {
                logger.warn("result set is null");
                throwable.printStackTrace();
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwable) {
                logger.warn("statement is null");
                throwable.printStackTrace();
            }

            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return users;
    }

    @Override
    public ArrayList<User> getStudentsOnCourse(int courseId) throws DAOException {
        ArrayList<User> students = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_STUDENTS_ON_COURSE);
            statement.setInt(1, courseId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                students.add(formUser(resultSet));
            }
        } catch (SQLException | UtilException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("can't close result set");
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("can't close result set");
            }

            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return students;
    }

    @Override
    public ArrayList<String> getTeacherSubjects(int teacherId) throws DAOException {
        ArrayList<String> subjects = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(
                    GET_TEACHER_SUBJECTS_BY_TEACHER_ID);
            statement.setInt(1, teacherId);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                subjects.add(resultSet.getString("subject_name"));
            }
        } catch (SQLException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("can't close result set");
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("can't close statement");
            }
            ConnectionPoolImpl.getInstance().returnConnection(connection);
        }

        return subjects;
    }

    private User formUser(ResultSet resultSet) throws SQLException, UtilException, DAOException {
        DateParser dateParser = new DateParser();

        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String birthdayNotParsed = resultSet.getString("birthday");
        Date birthday = dateParser.parse(birthdayNotParsed);
        String email = resultSet.getString("email");
        String role = resultSet.getString("role");
        ArrayList<String> teacherSubjects = getTeacherSubjects(id);

        return new User(id, firstName, lastName, email, birthday, teacherSubjects, role);
    }
}
