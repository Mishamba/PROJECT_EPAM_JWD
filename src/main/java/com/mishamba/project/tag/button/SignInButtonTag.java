package com.mishamba.project.tag.button;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import org.apache.log4j.Logger;

public class SignInButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(SignInButtonTag.class);

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<h2>Sign In</h2>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/sign_in\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"sign_in\">");
            out.write("<input type=\"submit\" value=\"Sign in\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
