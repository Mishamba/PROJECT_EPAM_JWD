package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.exception.ControllerException;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.service.impl.CustomServiceImpl;

import java.util.Properties;

public class GetCoursesCatalogCommand implements Command {
    @Override
    public String execute(Properties parameter) throws ControllerException {
        try {
            return CustomServiceImpl.getInstance().formCoursesCatalog();
        } catch (ServiceException e) {
            throw new ControllerException("can't get catalog info", e);
        }
    }
}
