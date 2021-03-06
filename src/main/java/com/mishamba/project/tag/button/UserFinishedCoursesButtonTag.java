package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserFinishedCoursesButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(UserFinishedCoursesButtonTag.class);

    private String buttonSign;

    public void setButtonSign(String buttonSign) {
        this.buttonSign = buttonSign;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/passed_courses\">");
            out.write("<input type=\"hidden\" name=\"command\" " +
                    "value=\"user_courses\">");
            out.write("<input type=\"hidden\" name=\"finished\" value=\"true\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(buttonSign);
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
