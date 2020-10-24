package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateHometaskProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(CreateHometaskProcessCommand.class);
    private final String INDEX_PAGE = "index.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String COURSE_ID = "course_id";
    private final String TITLE = "title";
    private final String DESCRIPTION = "description";
    private final String DEADLINE = "deadline";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX_PAGE;

        int courseId = (int) request.getAttribute(COURSE_ID);
        String title = request.getParameter(TITLE);
        String description = request.getParameter(DESCRIPTION);
        Date deadline = (Date) request.getAttribute(DEADLINE);

        try {
            if (ServiceFactory.getInstance().getHometaskService().
                    createHometask(courseId, title, description, deadline)) {
                logger.error("can't create hometask");
                uploadPage = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't create hometask");
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer page");
        }
    }
}
