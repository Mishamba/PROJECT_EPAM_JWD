package com.mishamba.project.service;

import com.mishamba.project.service.impl.CourseServiceImpl;
import com.mishamba.project.service.impl.HometaskServiceImpl;
import com.mishamba.project.service.impl.MarkReviewServiceImpl;
import com.mishamba.project.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final CourseService courseService = new CourseServiceImpl();
    private final HometaskService hometaskService = new HometaskServiceImpl();
    private final MarkReviewService markReviewService = new MarkReviewServiceImpl();
    private final UserService userService = new UserServiceImpl();

    private ServiceFactory() {}

    private static class CustomServiceFactoryHolder {
        private static final ServiceFactory HOLDER = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return CustomServiceFactoryHolder.HOLDER;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public HometaskService getHometaskService() {
        return hometaskService;
    }

    public MarkReviewService getMarkReviewService() {
        return markReviewService;
    }

    public UserService getUserService() {
        return userService;
    }
}
