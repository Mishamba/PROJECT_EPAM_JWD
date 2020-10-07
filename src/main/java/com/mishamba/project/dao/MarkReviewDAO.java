package com.mishamba.project.dao;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;

import java.util.ArrayList;

public interface MarkReviewDAO {
    ArrayList<MarkReview> getMarkReview(Course course, User user)
            throws DAOException;
}
