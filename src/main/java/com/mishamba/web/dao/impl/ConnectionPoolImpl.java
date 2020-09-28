package com.mishamba.web.dao.impl;

import com.mishamba.web.dao.ConnectionPool;
import com.mishamba.web.dao.ProxyConnection;
import com.mishamba.web.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPoolImpl implements ConnectionPool {
    private final Logger logger = Logger.getRootLogger();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private static final int DEFAULT_POOL_SIZE = 32;

    private ConnectionPoolImpl() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        // TODO: 9/28/20 get driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        /*Properties connectionProperties = new Properties();
        try(InputStream in = Files.newInputStream(
                Paths.get("resources/database.properties"))) { // TODO: 9/28/20 database.properties
            connectionProperties.load(in);
        } catch (IOException exception) {
            logger.error("can't get info from database.properties");
        }*/

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                /*freeConnections.add(new ProxyConnection(
                        DriverManager.getConnection(
                                connectionProperties.getProperty("url"),
                                connectionProperties)));*/
                freeConnections.add(new ProxyConnection(
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project_jwd",
                                "mishamba", "mishamba")));
            } catch (SQLException throwable) {
                logger.error("can't create connection");
                throwable.printStackTrace();
            }
        }
    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPoolImpl HOLDER = new ConnectionPoolImpl();
    }

    public static ConnectionPoolImpl getInstance() {
        return ConnectionPoolImpl.ConnectionPoolHolder.HOLDER;
    }

    public ProxyConnection getConnection() throws DAOException {
        ProxyConnection connectionToGive;
        try {
            connectionToGive = freeConnections.take();
            givenAwayConnections.offer(connectionToGive);
        } catch (InterruptedException e) {
            logger.error("can't give connection");
            Thread.currentThread().interrupt();
            throw new DAOException(e);
        }

        return connectionToGive;
    }

    public void returnConnection(ProxyConnection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException | InterruptedException throwables) {
                logger.error("can't close connection");
                throwables.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException throwable) {
                logger.error("can't deregister driver");
                throwable.printStackTrace();
            }
        });
    }

    public boolean checkForLose() {
        return (freeConnections.size() + givenAwayConnections.size() == 32);
    }

    public boolean checkForReturn() {
        return (freeConnections.size() == 32);
    }
}
