package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;

public interface ConnectionPool {
    ProxyConnection getConnection() throws DAOException;
    void returnConnection(ProxyConnection connection);
    boolean checkForLose();
    boolean checkForReturn();
}
