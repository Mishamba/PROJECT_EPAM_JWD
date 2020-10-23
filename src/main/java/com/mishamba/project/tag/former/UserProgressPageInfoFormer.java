package com.mishamba.project.tag.former;

import com.mishamba.project.model.User;
import com.mishamba.project.util.parser.impl.DateParser;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UserProgressPageInfoFormer extends TagSupport {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();

        try {
            JspWriter out = pageContext.getOut();

            out.write("<h3>First name</h3>");
            out.write("<br>");
            out.write(user.getFirstName());
            out.write("<br>");
            out.write("<h3>Last name</h3>");
            out.write("<br>");
            out.write(user.getLastName());
            out.write("<h3>Birthday</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(user.getBirthday()));
            out.write("<br>");
            out.write("<h3>Email</h3>");
            out.write("<br>");
            out.write(user.getEmail());
            out.write("<br>");
        } catch (IOException e) {
            throw new JspException("can't form user info");
        }

        return SKIP_BODY;
    }
}
