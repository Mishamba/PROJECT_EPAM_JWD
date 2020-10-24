package com.mishamba.project.dao;

import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;

public interface MarkReviewDAO {
    MarkReview getMarkReview(Course course, User user)
            throws DAOException;
    boolean createMarkReview(MarkReview markReview) throws DAOException;
}
