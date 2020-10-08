package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentProfile implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        String activeCourseButtonSign = properties.
                getProperty("activeCourseButtonSign");
        String passedCourseButtonSign = properties.
                getProperty("passedCoursesButtonSign");


        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserActiveCourses(activeCourseButtonSign));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserPassedCourses(passedCourseButtonSign));

        return builder.toString();
    }
}
