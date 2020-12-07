package com.mishamba.project.controller.command.impl;

import com.google.protobuf.ServiceException;
import com.mishamba.project.controller.command.Command;
import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class KickStudentProcessCommand implements Command {
    private static final Logger logger = Logger.getLogger(KickStudentProcessCommand.class);
    private final String INDEX_PAGE = "index.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String STUDENT_ID = "student_id";
    private final String COURSE_ID = "course_id";
    private final String FINISHED = "finished";
    private final String MARK = "mark";
    private final String REVIEW = "review";
    private final String GOT_CERTIFICATE = "got_certificate";
    private final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = INDEX_PAGE;

        int studentId = (int) request.getAttribute(STUDENT_ID);
        int courseId = (int) request.getAttribute(COURSE_ID);
        boolean finished = (boolean) request.getAttribute(FINISHED);
        int mark = (int) request.getAttribute(MARK);
        String review = request.getParameter(REVIEW);
        boolean gotCertificate = (boolean) request.getAttribute(GOT_CERTIFICATE);
        int teacherId = (int) request.getSession().getAttribute(ID);

        try {
            Course course = null;
            Optional optionalCourse = ServiceFactory.getInstance().getCourseService().getCourseById(courseId);
            if (optionalCourse.isPresent()) {
                course = (Course) optionalCourse.get();
            }

            User student = null;
            Optional optionalStudent = ServiceFactory.getInstance().getUserService().getUserById(studentId);
            if (optionalStudent.isPresent()) {
                student = (User) optionalStudent.get();
            }
            User teacher = null;
            Optional optionalTeacher = ServiceFactory.getInstance().getUserService().getUserById(teacherId);
            if (optionalTeacher.isPresent()) {
                teacher = (User) optionalTeacher.get();
            }
            if (!ServiceFactory.getInstance().getMarkReviewService().setMarkReview(course, student, teacher, mark, review, finished, gotCertificate)) {
                logger.error("can't write mark review");
                pageToUpload = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't write mark review");
            pageToUpload = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send response page");
            try {
                response.sendError(500);
            } catch (IOException ioException) {
                logger.error("can't set 500 error");
            }
        }
    }
}
