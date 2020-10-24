package com.mishamba.project.service;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;
import com.mishamba.project.exception.CustomServiceException;

import java.util.Optional;

public interface MarkReviewService {
    boolean setMarkReview(Course course, User student, User teacher, int mark, String review,
                          boolean finishedParameter,
                          boolean gotCretificateParameter)
            throws CustomServiceException;
    Optional<MarkReview> getMarkReview(int courseId, int studentId) throws CustomServiceException;
}
