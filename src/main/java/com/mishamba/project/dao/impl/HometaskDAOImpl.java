package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.HometaskDAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.HometaskResponse;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.DateParser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class HometaskDAOImpl implements HometaskDAO {
    private final Logger logger = Logger.getRootLogger();

    private final String SET_HOMETASK_MARK = "UPDATE hometask_responce SET " +
            "mark=? " +
            "WHERE hometask_id = ? AND student_id = ?";
    private final String ENTER_HOMETASK_RESPONSE = "INSERT INTO hometask_responce " +
            "(hometask_id, student_id, answer)" +
            "VALUES (?, ?, ?)";
    private final String GET_HOMETASK_BY_ID = "SELECT id, course_id, " +
            "hometask_title as title, hometask_description as description, image, begin_date, deadline " +
            "FROM hometasks " +
            "WHERE id=?";
    private final String CREATE_HOMETASK = "INSERT INTO hometasks " +
            "(course_id, hometask_title, hometask_description, " +
            "begin_date, deadline) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final String GET_HOMETASK_ON_COURSE = "SELECT id, course_id, " +
            "hometask_title as title, hometask_description as description, image, begin_date, " +
            "deadline " +
            "FROM hometasks " +
            "WHERE course_id = ?";
    private final String GET_HOMETASK_RESPONSE = "SELECT hometask_id, student_id, answer, mark " +
            "FROM hometask_responce " +
            "WHERE hometask_id = ? AND student_id = ?";

    @Override
    public void createHometask(Hometask hometask) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;

        DateParser dateParser = new DateParser();

        try {
            statement = connection.prepareStatement(CREATE_HOMETASK);
            statement.setInt(1, hometask.getCourseId());
            statement.setString(2, hometask.getTitle());
            statement.setString(3, hometask.getDescription());
            statement.setString(4, dateParser.parseDateToString(hometask.getBeginDate()));
            statement.setString(5, dateParser.parseDateToString(hometask.getDeadline()));
        } catch (SQLException e) {
            logger.error("can't form queue");
            throw new DAOException("can't form queue", e);
        }

        try {
            statement.execute();
        } catch (SQLException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue", e);
        }
    }

    @Override
    public ArrayList<Hometask> getHometasksOnCourseForUser(int courseId, int studentId) throws DAOException {
        ArrayList<Hometask> hometasks = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_HOMETASK_ON_COURSE);
            statement.setInt(1, courseId);

            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                hometasks.add(formHometask(resultSet));
            }

            for (Hometask hometask : hometasks) {
                HometaskResponse response = getHometaskResponse(hometask.getId(), studentId);
                hometask.setResponse(response);
            }
        } catch (SQLException | UtilException e) {
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

        return hometasks;
    }

    @Override
    public HometaskResponse getHometaskResponse(int hometaskId, int studentId) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_HOMETASK_RESPONSE);
            statement.setInt(1, hometaskId);
            statement.setInt(2, studentId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return formHometaskResponse(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue");
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
    }

    @Override
    public Hometask getHometaskById(int hometaskId) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(GET_HOMETASK_BY_ID);
            statement.setInt(1, hometaskId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return formHometask(resultSet);
            }
        } catch (SQLException | UtilException e) {
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

        return null;
    }

    @Override
    public void writeHometaskResponse(HometaskResponse response) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(ENTER_HOMETASK_RESPONSE);
            statement.setInt(1, response.getHometaskId());
            statement.setInt(2, response.getStudentId());
            statement.setString(3, response.getAnswer());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("can't write hometask responce");
        }
    }

    @Override
    public void setHometaskMark(int hometaskId, int studentId, int mark) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SET_HOMETASK_MARK);
            statement.setInt(1, mark);
            statement.setInt(2, studentId);
            statement.setInt(3, hometaskId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("can't execute queue", e);
        }
    }

    private HometaskResponse formHometaskResponse(ResultSet resultSet) throws SQLException {
        int hometaskId = resultSet.getInt("hometask_id");
        int studentId = resultSet.getInt("student_id");
        String answer = resultSet.getString("answer");
        Integer mark = resultSet.getInt("mark");

        return new HometaskResponse(hometaskId, studentId, answer, mark);
    }

    private Hometask formHometask(ResultSet resultSet) throws SQLException, UtilException {
        DateParser dateParser = new DateParser();

        int courseId = resultSet.getInt("course_id");
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Date beginDate = dateParser.parseDate(resultSet.getString("begin_date"));
        Date deadline = dateParser.parseDate(resultSet.getString("deadline"));

        return new Hometask(courseId, id, title, description, beginDate, deadline, null);
    }
}
