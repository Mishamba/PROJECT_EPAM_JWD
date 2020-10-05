package com.mishamba.project.util.former.builder;

import com.mishamba.project.util.exception.UtilException;

import java.util.Properties;

public class PartsBuilder {
    public String formCoursesCatalogButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<form action =\"PROJECT_EPAM_JWD_war/courses_catalog?command=courses_catalog\">");
        builder.append("<input type=\"submit\" value=\"Courses catalog\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formMainPageButton() {
        StringBuilder builder = new StringBuilder();
        builder.append("<form action=\"PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"submit\" value=\"Main page\">");
        builder.append("</form>");

        return builder.toString();
    }

    public String formSingInSingUpButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<h2>Sign In</h2>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/sing_in?command=sing_in\">");
        builder.append("<input type=\"submit\" value=\"Sign in\">");
        builder.append("</form><br>");
        builder.append("<h2>Sign Up<h2>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/sign_up?command=sing_up\">");
        builder.append("<input type=\"submit\" value=\"Sing Up\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formUserInfo(Properties properties) throws UtilException {
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
                append((String) properties.get("lastName")).
                append("</p><br>");

        return builder.toString();
    }

    public String formSignOutButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<br><form action=\"/PROJECT_EPAM_JWD_war/sing_out?command=sing_out>");
        builder.append("<input type=\"submit\" value=\"Sing Out\">");
        builder.append("</form><br>");

        return builder.toString();
    }
}
