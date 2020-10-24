package com.mishamba.project.dao;

import com.mishamba.project.exception.DAOException;

public interface ConnectionPool {
    ProxyConnection getConnection() throws DAOException;
    void returnConnection(ProxyConnection connection);
    boolean checkForLose();
}
