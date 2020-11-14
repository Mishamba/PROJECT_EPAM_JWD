package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeacherManageCourseButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(TeacherManageCourseButtonTag.class);

    private int courseId;
    private boolean finished;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sings/sing", locale);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"teacher_manage_course\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=");
            out.write(courseId);
            out.write(">");
            out.write("<input type=\"submit\" name=\"");
            if (finished) {
                out.write(resourceBundle.getString("managed_courses_sign"));
            } else {
                out.write(resourceBundle.getString("managing_courses_sign"));
            }
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button");
        }

        return SKIP_BODY;
    }
}
