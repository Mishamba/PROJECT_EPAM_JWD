package com.mishamba.project.service.former.builder.impl.anonym;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AnonymCourseProfileInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>anonym can't make any actions with course, " +
                "please sign in to</p><br>");
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSignInButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSignUpButton());

        return builder.toString();
    }
}
