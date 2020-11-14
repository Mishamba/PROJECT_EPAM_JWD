package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;

public class ChangeLocaleButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(ChangeLocaleButtonTag.class);
    private final String LOCALE = "locale";

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");

        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"change_locale\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(locale.getLanguage());
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
