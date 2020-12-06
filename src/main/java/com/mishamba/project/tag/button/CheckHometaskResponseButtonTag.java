package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CheckHometaskResponseButtonTag extends TagSupport {
    private final static Logger logger = Logger.getLogger(CheckHometaskResponseButtonTag.class);

    private int hometaskId;
    private int studentId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setHometaskId(int hometaskId) {
        this.hometaskId = hometaskId;
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign", locale);

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"check_hometask\">");
            out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"");
            out.write(String.valueOf(hometaskId));
            out.write("\">");
            out.write("<input type=\"hidden\" name=\"student_id\" value=\"");
            out.write(String.valueOf(studentId));
            out.write("\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("check_hometask_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
