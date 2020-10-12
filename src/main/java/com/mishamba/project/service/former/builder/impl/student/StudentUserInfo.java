package com.mishamba.project.service.former.builder.impl.student;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentUserInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        return PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserInfo(properties);
    }
}
