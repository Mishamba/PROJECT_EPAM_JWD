package com.mishamba.project.service.former.builder.impl.admin;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AdminCourseProfileButtons implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        if (properties == null) {
            throw new CustomServiceException("properties are null");
        }
        StringBuilder builder = new StringBuilder();

        int courseId = (int) properties.get("course_id");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formAppendTeacherOnCourseButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formRemoveTeacherFromCourseButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formFinishCourseButton(courseId));

        return builder.toString();
    }
}
