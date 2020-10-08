package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AdminCourseProfileButtons implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        if (properties == null) {
            throw new UtilException("properties ara null");
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
