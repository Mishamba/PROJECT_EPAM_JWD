package com.mishamba.project.tag.former;

import com.mishamba.project.model.User;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserInfoTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(UserInfoTag.class);

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<p>Role</p><br>");
            out.write("<p>");
            out.write(user.getRole());
            out.write("</p><br>");
            out.write("<p>First name</p><br>");
            out.write("<p>");
            out.write(user.getFirstName());
            out.write("</p><br>");
            out.write("<p>Second name</p><br>");
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
