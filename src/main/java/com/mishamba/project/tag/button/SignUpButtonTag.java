package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(SignUpButtonTag.class);

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);
        try {
            JspWriter out = pageContext.getOut();
            out.write("<h2>");
            out.write(resourceBundle.getString("sign_up_sign"));
            out.write("<h2>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/sign_up\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"sign_up\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("sign_up_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            throw new JspException("can't get button", e);
        }

        return SKIP_BODY;
    }
}
