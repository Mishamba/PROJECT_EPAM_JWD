package com.mishamba.project.util.former.builder.impl.student;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.part.CourseCatalogButton;

import java.util.Properties;

public class StudentMainPagesButtons implements Former {

    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append(CourseCatalogButton.get());

        return builder.toString();
    }
}
