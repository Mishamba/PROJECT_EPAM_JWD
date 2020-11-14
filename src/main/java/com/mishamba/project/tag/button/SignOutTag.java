package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignOutTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(SignOutTag.class);

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sings/sing", locale);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br><form action=\"/PROJECT_EPAM_JWD_war/sign_out\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"sign_out\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("sign_out_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
