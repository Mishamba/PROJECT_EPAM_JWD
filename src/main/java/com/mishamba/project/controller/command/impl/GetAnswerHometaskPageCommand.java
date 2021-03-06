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
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class sends hometask answer page
 *
 *  @version 1.0
 *  @author Mishamba
 */

public class GetAnswerHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetAnswerHometaskPageCommand.class);
    private final String HOMETASK_ID = "hometask_id";
    private final String HOMETASK = "hometask";
    private final String ANSWER_HOMETASK_PAGE = "pages/answer_hometask.jsp";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = ANSWER_HOMETASK_PAGE;
        int hometaskId = (int) request.getAttribute(HOMETASK_ID);

        Hometask hometask = null;
        try {
            Optional<Hometask> optionalHometask;
            optionalHometask = ServiceFactory.getInstance().getHometaskService().
                    getHometaskById(hometaskId);
            if (optionalHometask.isPresent()) {
                hometask = optionalHometask.get();
            }
        } catch (CustomServiceException | NoSuchElementException e) {
            logger.error("can't get hometask info");
            pageToUpload = ERROR_PAGE;
        }

        request.setAttribute(HOMETASK, hometask);

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload answer hometask page");
        }
    }
}
