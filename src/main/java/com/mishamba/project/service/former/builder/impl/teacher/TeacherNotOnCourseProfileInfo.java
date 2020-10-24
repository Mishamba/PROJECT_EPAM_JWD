package com.mishamba.project.service.former.builder.impl.teacher;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class TeacherNotOnCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();
        Integer courseId = (Integer) properties.get("courseId");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formTeacherManageCourseButton(courseId));

        return builder.toString();
    }
}
