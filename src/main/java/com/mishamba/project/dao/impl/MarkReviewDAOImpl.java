package com.mishamba.project.dao.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.MarkReviewDAO;
import com.mishamba.project.dao.ProxyConnection;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.MarkReview;
import com.mishamba.project.model.User;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.impl.DateParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MarkReviewDAOImpl implements MarkReviewDAO {
    private final String CREATE_MARK_REVIEW = "INSERT INTO student_mark_review " +
            "(student_id, teacher_id, course_id, finished, mark, finished_date, " +
            "review, got_certificate) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

    public MarkReview getMarkReview(Course course, User student)
            throws DAOException {
        if (course == null || student == null) {
            throw new DAOException("course of student is null");
        }

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPoolImpl.getInstance().
                    getConnection();
            statement = connection.prepareStatement(MARK_REVIEW_QUEUE);
            statement.setInt(1, student.getId());
            statement.setInt(2, student.getId());
            resultSet = statement.executeQuery();
            DateParser dateParser = new DateParser();

            int teacherId = resultSet.getInt("teacher_id");
            String teacherFirstName = resultSet.getString("teacher_first_name");
            String teacherLastName = resultSet.getString("teacher_last_name");
            Date teacherBirthday = dateParser.parse(resultSet.getString("teacher_birthday"));
            String teacherEmail = resultSet.getString("teacher_email");
            ArrayList<String> teacherSubjects = DAOFactory.getInstance().
                    getUserDAO().getTeacherSubjects(teacherId);
            User teacher = new User(teacherId, teacherFirstName,
                    teacherLastName, teacherEmail, teacherBirthday,
                    teacherSubjects, "teacher");
            Boolean finished = resultSet.getBoolean("finished");
            Integer mark = resultSet.getInt("mark");
            Date finishDate = dateParser.parse(resultSet.getString("finish_date"));
            String review = resultSet.getString("review");
            Boolean gotCertificate = resultSet.getBoolean("got_certificate");
            return new MarkReview(student, teacher, course,
                    finished, mark, finishDate, review, gotCertificate);
        } catch (SQLException | UtilException throwable) {
            throw new DAOException("can't get mark review", throwable);
        }
    }

    @Override
    public boolean createMarkReview(MarkReview markReview) throws DAOException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        PreparedStatement statement = null;

        DateParser dateParser = new DateParser();

        try {
            statement = connection.prepareStatement(CREATE_MARK_REVIEW);
            statement.setInt(1, markReview.getStudent().getId());
            statement.setInt(2, markReview.getTeacher().getId());
            statement.setInt(3, markReview.getCourse().getId());
            statement.setBoolean(4, markReview.getFinished());
            statement.setInt(5, markReview.getMark());
            statement.setString(6, dateParser.parseDateToString(
                    markReview.getFinishDate()));
            statement.setString(7, markReview.getReview());
            statement.setBoolean(8, markReview.getGotCertificate());

            return (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            throw new DAOException("can't create mark review", e);
        }

    }
}
