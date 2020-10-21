package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AddSubjectsForTeacherButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(AddSubjectsForTeacherButtonTag.class);

    private int teacherId;

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"add_subject\">");
            out.write("<input type=\"hidden\" name=\"teacher_id\" value=\"");
            out.write(teacherId);
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
