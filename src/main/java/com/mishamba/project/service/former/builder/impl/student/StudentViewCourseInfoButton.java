package com.mishamba.project.service.former.builder.impl.student;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentViewCourseInfoButton implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();
        int courseId = Integer.parseInt(properties.getProperty("courseId"));

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formViewCourseInfoButton(courseId));

        return builder.toString();
    }
}
