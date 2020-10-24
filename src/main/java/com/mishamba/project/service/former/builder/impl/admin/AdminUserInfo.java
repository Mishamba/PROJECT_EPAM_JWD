package com.mishamba.project.service.former.builder.impl.admin;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AdminUserInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        return PartsBuilderFactory.getInstance().getPartsBuilder().formUserInfo(properties);
    }
}
