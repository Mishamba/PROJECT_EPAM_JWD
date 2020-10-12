package com.mishamba.project.service.former.builder.impl.student;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class StudentProfileButtons implements Former {

    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();
        String activeCoursesButtonSign = properties.
                getProperty("activeCoursesButtonSign");
        String passedCoursesButtonSign = properties.
                getProperty("passedCoursesButtonSign");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserActiveCourses(activeCoursesButtonSign));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserPassedCourses(passedCoursesButtonSign));

        return builder.toString();
    }
}
