package com.mishamba.project.util.former.builder.impl.teacher;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.PartsBuilder;

import java.util.Properties;

public class TeacherMainPagesButtons implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append(PartsBuilder.getInstance().formCoursesCatalogButton());

        return builder.toString();
    }
}
