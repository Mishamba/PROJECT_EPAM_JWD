package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;
import com.mishamba.project.service.MarkReviewService;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Optional;

public class MarkReviewServiceImpl implements MarkReviewService {
    private static final Logger logger = Logger.getLogger(MarkReviewServiceImpl.class);

    @Override
    public boolean setMarkReview(Course course, User student, User teacher, int mark, String review, boolean finishedParameter, boolean gotCretificateParameter) throws CustomServiceException {
        if (course == null || student == null || teacher == null) {
            throw new CustomServiceException("parameters are null");
        }

        if (!teacher.getRole().equals("teacher") ||
                !student.getRole().equals("student")) {
            throw new CustomServiceException("given users hash wrong roles");
        }

        MarkReview markReview = new MarkReview(student, teacher, course,
                finishedParameter, mark, new Date(), review,
                gotCretificateParameter);

        try {
            MarkReview checkMarkReview = DAOFactory.getInstance().
                    getMarkReviewDAO().getMarkReview(course, student);
            if(!markReview.equals(checkMarkReview) && DAOFactory.getInstance().getMarkReviewDAO().createMarkReview(markReview)) {
                DAOFactory.getInstance().getCourseDAO().kickStudentFromCourse(student.getId(), course.getId());
                return true;
            }
        } catch (DAOException e) {
            logger.error("can't create mark review");
            throw new CustomServiceException("can't create mark review");

        }

        return false;
    }

    @Override
    public Optional<MarkReview> getMarkReview(int courseId, int studentId) throws CustomServiceException {
        return Optional.empty();
    }
}
