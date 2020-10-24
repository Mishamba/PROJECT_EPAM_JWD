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
import java.util.ArrayList;

public class GetCourseHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCourseHometaskPageCommand.class);
    private final String COURSE_HOMETASK_PAGE = "course_hometask_page.jsp";
    private final String ERROR_PAGE = "error.html";
    private final String ROLE = "role";
    private final String COURSE_ID = "courseId";
    private final String ID = "id";
    private final String HOMETASK_PARAMETER = "hometask";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = COURSE_HOMETASK_PAGE;

        int courseId = (int) request.getAttribute(COURSE_ID);
        int userId = (int) request.getSession().getAttribute(ID);
        String role = (String) request.getSession().getAttribute(ROLE);

        ArrayList<Hometask> hometasks = new ArrayList<>();
        try {
            hometasks = ServiceFactory.getInstance().getHometaskService().
                    getCourseHometaskForUser(courseId, userId, role);
        } catch (CustomServiceException e) {
            logger.error("can't get hometask list. setting error page");
            pageToUpload = ERROR_PAGE;
        }

        request.setAttribute(HOMETASK_PARAMETER, hometasks);

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send answer page");
        }
    }
}
