package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinishCourseButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(FinishCourseButtonTag.class);

    private int courseId;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action = \"/PROJECT_EPAM_JWD_war/course_profile\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"finish_course\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=\"");
            out.write(String.valueOf(courseId));
            out.write("\">");
            out.write("<input type=\"submit\" value = \"");
            out.write(resourceBundle.getString("finish_course_sign"));
            out.write("\">");
            out.write("</form>");
            out.write("<br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
