package com.mishamba.project.tag.former;

import com.mishamba.project.model.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserInfoTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(UserInfoTag.class);

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sings/sing", locale);

        try {
            JspWriter out = pageContext.getOut();
            out.write("<p>");
            out.write(resourceBundle.getString("role_sign"));
            out.write("</p><br>");
            out.write("<p>");
            out.write(user.getRole());
            out.write("</p><br>");
            out.write("<p>");
            out.write(resourceBundle.getString("first_name_sign"));
            out.write("</p><br>");
            out.write("<p>");
            out.write(user.getFirstName());
            out.write("</p><br>");
            out.write("<p>");
            out.write(resourceBundle.getString("last_name_sign"));
            out.write("</p><br>");
            out.write("<p>");
            out.write(user.getLastName());
            out.write("</p><br>");
        } catch (IOException e) {
            logger.error("can't form user info");
            throw new JspException("can't form button", e);
        }

        return SKIP_BODY;
    }
}
