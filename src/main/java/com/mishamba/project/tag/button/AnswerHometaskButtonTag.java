package com.mishamba.project.tag.button;

import com.mishamba.project.model.Hometask;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AnswerHometaskButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(AnswerHometaskButtonTag.class);

    private Hometask hometask;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs.sign");

        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"PROJECT_EPAM_JWD_war/hometask\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"answer_hometask\">");
            out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"");
            out.write(hometask.getId());
            out.write("\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("answer_hometask_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
