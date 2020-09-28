package com.mishamba.web.controller.impl;

import com.mishamba.web.controller.CustomController;
import com.mishamba.web.controller.exception.ControllerException;
import com.mishamba.web.service.exception.ServiceException;
import com.mishamba.web.service.impl.CustomServiceImpl;
import org.apache.log4j.Logger;

public class CustomControllerImpl implements CustomController {
    private static final Logger logger = Logger.getRootLogger();

    private static class CustomControllerImplHolder {
        private static final CustomControllerImpl HOLDER = new CustomControllerImpl();
    }

    public static CustomControllerImpl getInstance() {
        return CustomControllerImplHolder.HOLDER;
    }

    @Override
    public String execute() throws ControllerException {
        String add = null;
        try {
            add = CustomServiceImpl.getInstance().formCourseStringAnswer();
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("got exception");
        }

        return add;
    }
}