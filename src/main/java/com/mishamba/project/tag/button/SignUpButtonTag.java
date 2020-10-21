package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class SignUpButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(SignUpButtonTag.class);

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<h2>Sign Up<h2>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/sign_up\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"sign_up\">");
            out.write("<input type=\"submit\" value=\"Sign up\">");
            out.write("</form><br>");
        } catch (IOException e) {
            throw new JspException("can't get button", e);
        }

        return SKIP_BODY;
    }
}
