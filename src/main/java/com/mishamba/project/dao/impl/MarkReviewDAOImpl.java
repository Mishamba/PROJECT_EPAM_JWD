package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.MarkReviewDAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.DateParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MarkReviewDAOImpl implements MarkReviewDAO {
    private final String MARK_REVIEW_QUEUE = "SELECT " +
            "users.id as teacher_id" +
            "users.first_name AS teacher_first_name," +
            "users.last_name as teacher_last_name, finished, " +
            "users.id as teacher_id, user.email as teacher_email, " +
            "user.birthday as teacher_birthday, " +
            "mark, finished_date, review, got_certificate " +
            "FROM student_mark_review " +
            "LEFT JOIN " +
            "users " +
            "ON student_mark_review.teacher_id = users.id " +
            "WHERE student_mark_review.student_id = ? AND " +
            "student_mark_review.course_id = ?";

    public ArrayList<MarkReview> getMarkReview(Course course, User student)
            throws DAOException {
        if (course == null || student == null) {
            throw new DAOException("course of student is null");
        }

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<MarkReview> markReviews = new ArrayList<>();
        try {
            connection = ConnectionPoolImpl.getInstance().
                    getConnection();
            statement = connection.prepareStatement(MARK_REVIEW_QUEUE);
            statement.setInt(1, student.getId());
            statement.setInt(2, student.getId());
            resultSet = statement.executeQuery();
            DateParser dateParser = new DateParser();

            while(resultSet.next()) {
                Integer teacher_id = resultSet.getInt("teacher_id");
                String teacherFirstName = resultSet.getString("teacher_first_name");
                String teacherLastName = resultSet.getString("teacher_last_name");
                Date teacherBirthday = dateParser.parseDate(resultSet.getString("teacher_birthday"));
                String teacherEmail = resultSet.getString("teacher_email");
                User teacher = new User(teacher_id, teacherFirstName,
                        teacherLastName, teacherEmail, teacherBirthday,
                        "teacher");
                Boolean finished = resultSet.getBoolean("finished");
                Integer mark = resultSet.getInt("mark");
                Date finishDate = dateParser.parseDate(resultSet.getString("finish_date"));
                String review = resultSet.getString("review");
                Boolean gotCertificate = resultSet.getBoolean("got_certificate");
                markReviews.add(new MarkReview(student, teacher, course,
                        finished, mark, finishDate, review, gotCertificate));
            }
        } catch (SQLException | UtilException throwable) {
            throw new DAOException("can't get mark review", throwable);
        }

        return markReviews;
    }
}
