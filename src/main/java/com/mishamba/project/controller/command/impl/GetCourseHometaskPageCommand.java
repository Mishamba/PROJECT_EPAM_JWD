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

/**
 * This class sends page with all hometasks on course
 *
 * @version 1.0
 * @author Mishamba
 */

public class GetCourseHometaskPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetCourseHometaskPageCommand.class);
    private final String COURSE_HOMETASK_PAGE = "pages/course_hometask_page.jsp";
    private final String ERROR_PAGE = "pages/error.html";
    private final String ROLE = "role";
    private final String COURSE_ID = "course_id";
    private final String ID = "id";
    private final String HOMETASK_PARAMETER = "hometasks";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = COURSE_HOMETASK_PAGE;

        Integer courseId = (Integer) request.getAttribute(COURSE_ID);
        Integer userId = (Integer) request.getSession().getAttribute(ID);
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
