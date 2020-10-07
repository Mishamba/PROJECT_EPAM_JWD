package com.mishamba.project.dao;

import com.mishamba.project.dao.impl.CourseDAOImpl;
import com.mishamba.project.dao.impl.HometaskDAOImpl;
import com.mishamba.project.dao.impl.ProgramStepDAOImpl;
import com.mishamba.project.dao.impl.UserDAOImpl;

public class DAOFactory {
    private DAOFactory() {}

    private static class DAOFactoryHolder {
        private static final DAOFactory HOLDER = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactoryHolder.HOLDER;
    }

    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    public CourseDAO getCourseDAO() {
        return new CourseDAOImpl();
    }

    public HometaskDAO getHometaskDAO() {
        return new HometaskDAOImpl();
    }

    public ProgramStepDAO getProgramStepDAO() {
        return new ProgramStepDAOImpl();
    }
}
