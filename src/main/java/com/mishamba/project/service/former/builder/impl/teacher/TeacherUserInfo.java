package com.mishamba.project.service.former.builder.impl.teacher;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class TeacherUserInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        return PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserInfo(properties);
    }
}
