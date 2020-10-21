package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CheckHometaskButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(CheckHometaskButtonTag.class);

    private int hometaskId;
    private int studentId;

    public void setHometaskId(int hometaskId) {
        this.hometaskId = hometaskId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
            out.write("<input type=\"hidden\" name=\"command\" " +
                    "value=\"check_hometask\">");
            out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"");
            out.write(hometaskId);
            out.write("\">");
            out.write("<input type=\"hidden\" name=\"student_id\" value=\"");
            out.write(studentId);
            out.write("\">");
            out.write("<input type=\"submit\" value=\"Check hometask\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can' form button", e);
        }

        return SKIP_BODY;
    }
}
