package com.mishamba.project.util.former.builder.part;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class UserInfo {
    public static String get(Properties properties) throws UtilException {
        if (properties == null) {
            throw new UtilException("given session is null");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<p>Role</p><br>");
        builder.append("<p>").
                append((String) properties.get("role")).
                append("</p><br>");
        builder.append("<p>First name</p><br>");
        builder.append("<p>").
                append((String) properties.get("firstName")).
                append("</p><br>");
        builder.append("<p>Second name</p><br>");
        builder.append("<p>").
                append((String) properties.get("secondName")).
                append("</p><br>");

        return builder.toString();
    }
}
