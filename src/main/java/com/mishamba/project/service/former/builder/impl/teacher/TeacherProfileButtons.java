package com.mishamba.project.util.former.builder.impl.teacher;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class TeacherProfileButtons implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();
        int teacherId = Integer.parseInt((String) properties.get("userId"));
        String activeCoursesButtonSign = properties.
                getProperty("activeCoursesButtonSign");
        String passedCoursesButtonSign = properties.
                getProperty("passedCoursesButtonSign");

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserActiveCourses(activeCoursesButtonSign));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserPassedCourses(passedCoursesButtonSign));
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formAddSubjectsForTeacher(teacherId));

        return builder.toString();
    }
}
