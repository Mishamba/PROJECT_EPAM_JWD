package com.mishamba.project.service;

import com.mishamba.project.service.impl.CustomServiceImpl;

public class CustomServiceFactory {
    private final CustomService customService = new CustomServiceImpl();

    private CustomServiceFactory() {}

    private static class CustomServiceFactoryHolder {
        private static final CustomServiceFactory HOLDER = new CustomServiceFactory();
    }

    public static CustomServiceFactory getInstance() {
        return CustomServiceFactoryHolder.HOLDER;
    }

    public CustomService getCustomService() {
        return customService;
    }
}
