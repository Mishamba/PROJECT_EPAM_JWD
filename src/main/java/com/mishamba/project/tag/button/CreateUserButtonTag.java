package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateUserButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(CreateUserButtonTag.class);

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign",
                locale);

        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"sign_up\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("sign_up_someone_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button");
        }

        return SKIP_BODY;
    }
}
