package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.HometaskDAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Hometask;
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

    private final String CREATE_HOMETASK = "INSERT INTO hometasks " +
            "(course_id, hometask_title, hometask_description, " +
            "begin_date, deadline) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final String GET_HOMETASK_ON_COURSE = "SELECT id, course_id, " +
            "hometask_title, hometask_description, image, begin_date, " +
            "deadline " +
            "FROM hometasks " +
            "WHERE course_id = ?";

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
    public ArrayList<Hometask> getHometasksOnCourse(int courseId) throws DAOException {
        ArrayList<Hometask> hometasks = new ArrayList<>();
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(GET_HOMETASK_ON_COURSE);
            statement.setInt(1, courseId);
        } catch (SQLException e) {
            logger.error("can't form queue");
            throw new DAOException("can't form queue", e);
        }

        try {
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                hometasks.add(formHometask(resultSet));
            }
        } catch (SQLException | UtilException e) {
            logger.error("can't execute queue");
            throw new DAOException("can't execute queue", e);
        }

        return hometasks;
    }

    private Hometask formHometask(ResultSet resultSet) throws SQLException, UtilException {
        DateParser dateParser = new DateParser();

        int courseId = resultSet.getInt("course_id");
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Date beginDate = dateParser.parseDate(resultSet.getString("beginDate"));
        Date deadline = dateParser.parseDate(resultSet.getString("deadline"));

        return new Hometask(courseId, id, title, description, beginDate, deadline);
    }
}
