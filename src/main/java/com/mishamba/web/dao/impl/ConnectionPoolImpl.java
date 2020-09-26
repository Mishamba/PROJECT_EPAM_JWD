package com.mishamba.web.dao.impl;

import com.mishamba.web.dao.ConnectionPool;
import com.mishamba.web.dao.ProxyConnection;
import com.mishamba.web.dao.exception.DAOException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPoolImpl implements ConnectionPool {
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private static final String URL = "jdbc:mysql://localhost:3306/final_project_jwd ? serverTimezone=UTC";
    private static final String USER = "mishamba";
    private static final String PASSWORD = "Alqp1209";
    private static final int DEFAULT_POOL_SIZE = 32;

    private ConnectionPoolImpl() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", USER);
        connectionProperties.put("password", PASSWORD);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(URL, connectionProperties)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
