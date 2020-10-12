package com.mishamba.project.service.former.builder.impl.anonym;

import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;

import java.util.Properties;

public class AnonymMenuButtons implements Former {

    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formMainPageButton());
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formCoursesCatalogButton());

        return builder.toString();
    }
}
