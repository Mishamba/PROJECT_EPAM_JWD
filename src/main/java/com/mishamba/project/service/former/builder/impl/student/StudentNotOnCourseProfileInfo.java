package com.mishamba.project.service.former.builder.impl.student;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentNotOnCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();
        int courseId = Integer.parseInt(properties.getProperty("courseId"));

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formStudentSignUpForCourseButton(courseId));

        return builder.toString();
    }
}
