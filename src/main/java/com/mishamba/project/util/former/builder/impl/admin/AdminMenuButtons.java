package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AdminMenuButtons implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
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
