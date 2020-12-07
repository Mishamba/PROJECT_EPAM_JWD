package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppendTeacherOnCourseProcessCommand implements Command {
    private final static Logger logger = Logger.getLogger(AppendTeacherOnCourseProcessCommand.class);
    private final String COURSE_ID = "course_id";
    private final String INDEX = "index.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ID = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX;

        int courseId = (int) request.getAttribute(COURSE_ID);
        int userId = (int) request.getSession().getAttribute(ID);

        try {
            ServiceFactory.getInstance().getCourseService().appendTeacherOnCoruse(courseId, userId);
        } catch (CustomServiceException e) {
            logger.error("can't append teacher on course", e);
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send response");
            try {
                response.sendError(500);
            } catch (IOException ioException) {
                logger.error("can't send error 500");
            }
        }
    }
}
