package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.PartsBuilder;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentOnCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        int courseId;
        if (properties.get("courseId") != null) {
            courseId = Integer.parseInt(properties.getProperty("courseId"));
        } else {
            throw new UtilException("no courseId given");
        }

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formCourseHometasksButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formStudentLeaveCourse(courseId));

        return builder.toString();
    }
}
