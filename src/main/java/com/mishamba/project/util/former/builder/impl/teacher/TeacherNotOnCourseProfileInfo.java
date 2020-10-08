package com.mishamba.project.util.former.builder.impl.teacher;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class TeacherNotOnCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        Integer courseId = (Integer) properties.get("courseId");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formTeacherManageCourseButton(courseId));

        return builder.toString();
    }
}
