package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HometaskFormerTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(HometaskFormerTag.class);

    private Hometask hometask;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }
    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>Hometask title</h3>");
            out.write("<br>");
            out.write(hometask.getTitle());
            out.write("<br>");
            out.write("<h3>Hometask description</h3>");
            out.write("<br>");
            out.write(hometask.getDescription());
            out.write("<br>");
            out.write("<h3>Deadline</h3>");
            out.write(dateParser.parseDateToString(hometask.getDeadline()));
            out.write("<br>");
        } catch (IOException e) {
            throw new JspException("can't form hometask", e);
        }

        return SKIP_BODY;
    }
}
