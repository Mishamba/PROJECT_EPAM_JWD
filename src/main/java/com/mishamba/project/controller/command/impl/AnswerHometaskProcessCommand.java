package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.service.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AnswerHometaskProcessCommand implements Command {
    private final static Logger logger = Logger.getLogger(AnswerHometaskProcessCommand.class);
    private final String HOMETASK_ID = "hometask_id";
    private final String ANSWER = "answer";
    private final String ID = "id";
    private final String STUDENT = "student";
    private final String ROLE = "role";
    private final String ERROR_PAGE = "error.html";
    private final String INDEX_PAGE = "index.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String uploadPage = INDEX_PAGE;

        int hometaskId = getHometaskId(request);
        String answer = request.getParameter(ANSWER);
        int studentId = getUserId(request);

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

    private int getHometaskId(HttpServletRequest request) {
        String hometaskId = request.getParameter(HOMETASK_ID);
        try {
            return Integer.parseInt(hometaskId);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("can't parse hometask id. setting 0");
            return 0;
        }
    }

    private int getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (int) session.getAttribute(ID);
    }
}
