package com.mishamba.project.service.former.builder;

import com.mishamba.project.service.exception.CustomServiceException;

import java.util.Properties;

public interface Former {
    String form(Properties properties) throws CustomServiceException;
}
