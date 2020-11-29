package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * This class sends check hometask page for teacher.
 * Also it validates user role
 *
 *  @version 1.0
 *  @author Mishamba
 */

public class GetCheckHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCheckHometaskPageCommand.class);
    private final String CHECK_HOMETASK_PAGE = "pages/check_hometask.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String HOMETASK_ID = "hometask_id";
    private final String STUDENT_ID = "student_id";
    private final String HOMETASK_DATA = "hometask_data";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String answerPage = CHECK_HOMETASK_PAGE;

        int hometaskId = (int) request.getAttribute(HOMETASK_ID);
        int studentId = (int) request.getAttribute(STUDENT_ID);

        Hometask hometaskData = null;
        try {
            Optional<Hometask> optionalHometask;
            optionalHometask = ServiceFactory.getInstance().getHometaskService().
                    getStudentHometaskWithResponce(hometaskId, studentId);
            if (optionalHometask.isPresent()) {
                hometaskData = optionalHometask.get();
            }
        } catch (CustomServiceException e) {
            answerPage = ERROR_PAGE;
        }

        request.setAttribute(HOMETASK_DATA, hometaskData);

        try {
            request.getRequestDispatcher(answerPage).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send answer page");
        }
    }
}
