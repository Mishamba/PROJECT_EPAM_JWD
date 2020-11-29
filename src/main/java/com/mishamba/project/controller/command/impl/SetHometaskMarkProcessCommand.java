package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class processes setting mark on students hometask responce
 *
 * @version 1.0
 * @author Mishamba
 */

public class SetHometaskMarkProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SetHometaskMarkProcessCommand.class);
    private final String INDEX_PAGE = "pages/index.jsp";
    private final String HOMETASK_ID = "hometask_id";
    private final String MARK = "mark";
    private final String STUDENT_ID = "student_id";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX_PAGE;

        int hometaskId = (int) request.getAttribute(HOMETASK_ID);
        int mark = (int) request.getAttribute(MARK);
        int studentId = (int) request.getAttribute(STUDENT_ID);

        try {
            if (!ServiceFactory.getInstance().getHometaskService().
                    setHometaskMark(hometaskId, studentId, mark)) {
                logger.error("can't set hometask mark");
                uploadPage = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't set hometask mark");
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload page");
        }
    }
}
