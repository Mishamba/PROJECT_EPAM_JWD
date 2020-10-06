package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        Integer courseId = (Integer) properties.get("course_id");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formStudentSingUpForCourseButton(courseId));

        return builder.toString();
    }
}
