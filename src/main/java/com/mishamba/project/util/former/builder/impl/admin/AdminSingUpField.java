package com.mishamba.project.util.former.builder.impl.admin;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class AdminSingUpField implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append("<p>role</p><br>").
                append("<form>").
                append("<input type=\"text\" name=\"role\">").
                append("</form><br>");

        return builder.toString();
    }
}
