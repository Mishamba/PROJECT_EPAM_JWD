package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.PartsBuilder;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentUserInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        return PartsBuilderFactory.getInstance().getPartsBuilder().formUserInfo(properties);
    }
}
