package com.mishamba.project.service.former.builder.impl.admin;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AdminMenuButtons implements Former {
    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formMainPageButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formSignOutButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formUserProfileButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formCreateUserButton());

        return builder.toString();
    }
}
