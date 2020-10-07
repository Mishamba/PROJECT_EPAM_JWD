package com.mishamba.project.dao;

import com.mishamba.project.dao.impl.CourseDAOImpl;
import com.mishamba.project.dao.impl.HometaskDAOImpl;
import com.mishamba.project.dao.impl.ProgramStepDAOImpl;
import com.mishamba.project.dao.impl.UserDAOImpl;

public class DAOFactory {
    private final UserDAO userDAO = new UserDAOImpl();
    private final CourseDAO courseDAO = new CourseDAOImpl();
    private final HometaskDAO hometaskDAO = new HometaskDAOImpl();
    private final ProgramStepDAO programStepDAO = new ProgramStepDAOImpl();

    private DAOFactory() {}

    private static class DAOFactoryHolder {
        private static final DAOFactory HOLDER = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHolder.HOLDER;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }

    public HometaskDAO getHometaskDAO() {
        return hometaskDAO;
    }

    public ProgramStepDAO getProgramStepDAO() {
        return programStepDAO;
    }
}
