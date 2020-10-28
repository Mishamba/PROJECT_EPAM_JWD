package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.User;
import com.mishamba.project.service.ServiceFactory;
import com.mishamba.project.exception.CustomServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GetMainPageCommand implements Command {
    private final Logger logger = Logger.getLogger(GetMainPageCommand.class);
    private final String USER_INFO_PARAMETER = "user";
    private final String COURSES_ADD_PARAMETER = "courses_add";
    private final String MAIN_PAGE = "pages/main.jsp";
    private final String ID = "id";
    private final String ERROR_PAGE = "pages/error.html";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse resp) {
        String uploadPage = MAIN_PAGE;

        int userId = (int) request.getSession().getAttribute(ID);

        User user = null;
        try {
            Optional<User> optionalUser;
            optionalUser = ServiceFactory.getInstance().getUserService().getUserById(userId);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
        } catch (CustomServiceException e) {
            uploadPage = ERROR_PAGE;
        }

        ArrayList<Course> coursesAdd;
        try {
            coursesAdd = ServiceFactory.getInstance().getCourseService().
                    getCoursesWithoutTeacher();
        } catch (CustomServiceException e) {
            logger.error("can't get courses add");
            coursesAdd = new ArrayList<>();
            uploadPage = ERROR_PAGE;
        }

        request.setAttribute(USER_INFO_PARAMETER, user);
        request.setAttribute(COURSES_ADD_PARAMETER, coursesAdd);

        try {
            request.getRequestDispatcher(uploadPage).forward(request, resp);
        } catch (ServletException | IOException e) {
            logger.error("can't send response for user");
        }
    }
}
