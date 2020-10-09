package com.mishamba.project.util.former.builder.impl.teacher;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class TeacherOnCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        int courseId;
        try {
            courseId = Integer.parseInt(properties.getProperty("courseId"));
        } catch (NumberFormatException | NullPointerException e) {
            throw new UtilException("course id parameter is null or smth else");
        }

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formStudentsListButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formCourseHometasksButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formCreateHometaskButton(courseId));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formFinishCourseButton(courseId));

        return builder.toString();
    }
}
