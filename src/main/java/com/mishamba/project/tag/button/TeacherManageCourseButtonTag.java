package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TeacherManageCourseButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(TeacherManageCourseButtonTag.class);

    private int courseId;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"teacher_manage_course\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=");
            out.write(courseId);
            out.write(">");
            out.write("<input type=\"submit\" name=\"Manage course\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button");
        }

        return SKIP_BODY;
    }
}
