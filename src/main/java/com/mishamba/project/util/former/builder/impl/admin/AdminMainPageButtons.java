package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.builder.PartsBuilder;

import java.util.Properties;

public class AdminMainPageButtons implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append(PartsBuilder.getInstance().formMainPageButton());

        return builder.toString();
    }
}
