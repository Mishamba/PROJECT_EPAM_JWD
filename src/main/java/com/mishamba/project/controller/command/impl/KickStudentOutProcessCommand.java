package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KickStudentOutProcessCommand implements Command {
    private static final Logger logger = Logger.getLogger(KickStudentOutProcessCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String answerPage = "index.jsp";
        if (!userIsTeacher(request)) {
            answerPage = "error.html";
        }

        // TODO: 10/21/20 get student, teacher as User and create markReview
    }

    private boolean userIsTeacher(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        return (role != null && role.equals("teacher"));
    }

    private boolean getFinishedParameter(HttpServletRequest request) {
        String finished = request.getParameter("finished");
        return Boolean.parseBoolean(finished);
    }

    private int getMark(HttpServletRequest request) {
        String mark = request.getParameter("mark");
        try {
            return Integer.parseInt(mark);
        } catch (NullPointerException | NumberFormatException e) {
            logger.error("can't parse mark. return 0");
            return 0;
        }
    }

    private boolean getGotCertificateParameter(HttpServletRequest request) {
        String gotCertificate = request.getParameter("got_certificate");
        return Boolean.parseBoolean(gotCertificate);
    }
}
