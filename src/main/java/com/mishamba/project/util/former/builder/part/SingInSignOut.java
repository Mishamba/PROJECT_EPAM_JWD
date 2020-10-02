package com.mishamba.project.util.former.builder.part;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;

import java.util.Properties;

public class SingInSignOut {
    public String get() throws UtilException {
        StringBuilder builder = new StringBuilder();

        builder.append("<h2>Sign In</h2>");
        builder.append("<form action=\"PROJECT_EPAM_JWD_war/sing_in\">");
        builder.append("<input type=\"submit\" value=\"Sign in\">");
        builder.append("</form><br>");
        builder.append("<h2>Sign Up<h2>");
        builder.append("<form action=\"PROJECT_EPAM_JWD_war/sign_up\">");
        builder.append("<input type=\"submit\" value=\"Sing Up\">");
        builder.append("</form><br>");

        return builder.toString();
    }
}
