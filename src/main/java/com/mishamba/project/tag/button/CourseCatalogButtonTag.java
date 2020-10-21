package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CourseCatalogButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(
            CourseCatalogButtonTag.class);

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<form action =\"/PROJECT_EPAM_JWD_war/courses_catalog\">");
            out.write("<input type=\"hidden\" name=\"command\" " +
                    "value=\"courses_catalog\">");
            out.write("<input type=\"submit\" value=\"Courses catalog\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
