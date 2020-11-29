package com.mishamba.project.tag.former;

import com.mishamba.project.model.Course;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CourseListFormerTag extends TagSupport {
    private Course course;

    public void setCourse(Course course) {
        this.course = course;
    }

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);
        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>" + course.getCourseName() + "</h3>");
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/course_info\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"course_profile\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=\"");
            out.write(String.valueOf(course.getId()));
            out.write("\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("view_sign"));
            out.write("\">");
            out.write("</form>");
            out.write("<br>");
        } catch (IOException e) {
            throw new JspException("can't form course", e);
        }

        return SKIP_BODY;
    }
}
