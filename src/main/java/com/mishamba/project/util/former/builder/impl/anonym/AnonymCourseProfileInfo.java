package com.mishamba.project.util.former.builder.impl.anonym;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AnonymCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>anonym can't make any actions with course, " +
                "please sing in to</p><br>");
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSingInSingUpButton());

        return builder.toString();
    }
}
