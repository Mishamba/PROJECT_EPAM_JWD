package com.mishamba.web.dao;

import com.mishamba.web.dao.exception.DAOException;

public interface ConnectionPool {
    ProxyConnection getConnection() throws DAOException;
    void returnConnection(ProxyConnection connection);
    boolean checkForLose();
    boolean checkForReturn();
}
