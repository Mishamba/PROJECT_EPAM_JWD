package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CourseCatalogButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(
            CourseCatalogButtonTag.class);

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sings/sing", locale);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<form action =\"/PROJECT_EPAM_JWD_war/courses_catalog\">");
            out.write("<input type=\"hidden\" name=\"command\" " +
                    "value=\"courses_catalog\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("courses_catalog_button_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
