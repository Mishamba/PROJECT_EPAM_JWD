package com.mishamba.project.util.former.builder.impl.anonym;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class AnonymUserInfo implements Former {
    @Override
    public String form(Properties properties) throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append("<h2>Sing In</h2><br>");
        builder.append("form action=\"/PROJECT_EPAM_JWD_war/sign_in?command=authorization\">");
        builder.append("<input type=\"submit\" value=\"Sign in\">");
        builder.append("</form>");
        builder.append("<h2>Sing Up</h2>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/sign_up?command=registration\">");
        builder.append("<input type=\"submit\" value=\"Sign up\">");
        builder.append("</form>");

        return builder.toString();
    }
}
