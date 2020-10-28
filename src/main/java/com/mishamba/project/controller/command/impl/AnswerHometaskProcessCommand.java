package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnswerHometaskProcessCommand implements Command {
    private final static Logger logger = Logger.getLogger(AnswerHometaskProcessCommand.class);
    private final String HOMETASK_ID = "hometask_id";
    private final String ANSWER = "answer";
    private final String ID = "id";
    private final String ERROR_PAGE = "pages/error.html";
    private final String INDEX_PAGE = "pages/index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX_PAGE;

        int hometaskId = (int) request.getAttribute(HOMETASK_ID);
        String answer = request.getParameter(ANSWER);
        int studentId = (int) request.getSession().getAttribute(ID);

        try {
            if (ServiceFactory.getInstance().getHometaskService().writeHometaskAnswer(answer, hometaskId, studentId)) {
                logger.error("can't write answer");
                uploadPage = ERROR_PAGE;
            }
        } catch (CustomServiceException e) {
            logger.error("can't write answer");
            uploadPage = ERROR_PAGE;
        }

        try {
            request.getRequestDispatcher(uploadPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send main page");
        }
    }
}
