package com.mishamba.project.util.former.builder.impl.anonym;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class AnonymSignUpField implements Former {

    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>Role : Student</p><br>");

        return builder.toString();
    }
}
