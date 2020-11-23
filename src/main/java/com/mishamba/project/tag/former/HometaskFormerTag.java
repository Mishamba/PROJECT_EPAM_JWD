package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HometaskFormerTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(HometaskFormerTag.class);

    private Hometask hometask;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }

    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("hometask_title_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(hometask.getTitle());
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("hometask_description_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(hometask.getDescription());
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("deadline_sign"));
            out.write("</h3>");
            out.write(dateParser.parseDateToString(hometask.getDeadline()));
            out.write("<br>");
        } catch (IOException e) {
            logger.error("can't form hometask");
            throw new JspException("can't form hometask", e);
        }

        return SKIP_BODY;
    }
}
