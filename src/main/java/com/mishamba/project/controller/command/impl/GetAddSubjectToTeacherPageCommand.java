package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAddSubjectToTeacherPageCommand implements Command {
    private final Logger logger = Logger.getLogger(
            GetAddSubjectToTeacherPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
    }
}
