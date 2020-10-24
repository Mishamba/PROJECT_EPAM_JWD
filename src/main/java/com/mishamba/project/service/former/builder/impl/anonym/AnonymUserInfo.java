package com.mishamba.project.service.former.builder.impl.anonym;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AnonymUserInfo implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSignInButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSignUpButton());

        return builder.toString();
    }
}
