package com.mishamba.project.service.former.builder.impl.anonym;

import com.mishamba.project.exception.CustomServiceException;
import com.mishamba.project.service.former.builder.Former;

import java.util.Properties;

public class AnonymSignUpField implements Former {

    @Override
    public String form(Properties properties) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>Role : Student</p><br>");

        return builder.toString();
    }
}
