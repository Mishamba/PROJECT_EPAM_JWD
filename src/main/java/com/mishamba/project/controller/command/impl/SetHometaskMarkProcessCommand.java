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

public class SetHometaskMarkProcessCommand implements Command {
    private final Logger logger = Logger.getLogger(SetHometaskMarkProcessCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String pageToUpload = "index.jsp";

        if (!checkForTeacher(request)) {
            logger.warn("not teacher user tries to mark hometask");
            pageToUpload = "error.html";
        }

        int hometaskId = getHometaskId(request);
        int mark = getMark(request);
        int studentId = getStudentId(request);

        try {
            if (!ServiceFactory.getInstance().getCustomService().setHometaskMark(hometaskId, studentId, mark)) {
                logger.error("can't set hometask mark");
                pageToUpload = "error.html";
            }
        } catch (CustomServiceException e) {
            logger.error("can't set hometask mark");
        }

        try {
            request.getRequestDispatcher(pageToUpload).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't upload page");
        }
    }

    private boolean checkForTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        return role.equals("teacher");
    }

    private int getStudentId(HttpServletRequest request) {
        String studentId = request.getParameter("student_id");
        try {
            return Integer.parseInt(studentId);
        } catch (NullPointerException | NumberFormatException e) {
            return 0;
        }
    }

    private int getHometaskId(HttpServletRequest request) {
        String hometaskId = request.getParameter("hometask_id");
        try {
            return Integer.parseInt(hometaskId);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("can't get hometask id .setting 0");
            return 0;
        }
    }

    private int getMark(HttpServletRequest request) {
        String mark = request.getParameter("mark");
        try {
            return Integer.parseInt(mark);
        } catch (NumberFormatException | NullPointerException e) {
            logger.error("can't get mark");
            return 0;
        }
    }
}
