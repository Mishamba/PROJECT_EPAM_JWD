package com.mishamba.project.dao;

import com.mishamba.project.dao.impl.*;

public class DAOFactory {
    private final UserDAO userDAO = new UserDAOImpl();
    private final CourseDAO courseDAO = new CourseDAOImpl();
    private final HometaskDAO hometaskDAO = new HometaskDAOImpl();
    private final ProgramStepDAO programStepDAO = new ProgramStepDAOImpl();
    private final MarkReviewDAO markReviewDAO = new MarkReviewDAOImpl();

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

    public MarkReviewDAO getMarkReviewDAO() {
        return markReviewDAO;
    }
}
