package com.mishamba.project.util.former.builder.part;

public class CoursesAdd {
    public static String get() {
        StringBuilder builder = new StringBuilder();

        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/courses_catalog?command=courses_catalog\">");
        builder.append("<input type=\"submit\" value=\"Courses Catalog\">");
        builder.append("</form><br>");

        return builder.toString();
    }
}
