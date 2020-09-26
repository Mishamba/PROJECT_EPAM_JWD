package com.mishamba.web.dao.impl;

import com.mishamba.web.dao.DAO;
import com.mishamba.web.dao.ProxyConnection;
import com.mishamba.web.dao.exception.DAOException;
import com.mishamba.web.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DAOImpl implements DAO {
    private static final String STUDENT_QUEUE =
            "SELECT id, first_name, last_name, email, birthday " +
            "FROM users WHERE role = 'student' " +
                    "AND first_name = ? " +
                    "AND last_name = ?";
    private static final String COURSES_WITHOUT_TEACHER = "";

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
                throwables.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException throwables) {
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
        return null;
    }

    @Override
    public ArrayList<Course> getActiveCourses() throws DAOException {
        return null;
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
