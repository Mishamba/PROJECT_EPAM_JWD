package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HometaskForCheckFormer extends TagSupport {
    private static final Logger logger = Logger.
            getLogger(HometaskForCheckFormer.class);
    private Hometask hometask;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();

            HometaskFormerTag hometaskFormerTag = new HometaskFormerTag();
            hometaskFormerTag.setHometask(hometask);

            hometaskFormerTag.doStartTag();

            out.write("<br>");
            out.write("<h3>Answer</h3>");
            out.write("<br>");
            out.write(hometask.getResponse().getAnswer());
            out.write("<br>");
        } catch (IOException e) {
            logger.error("can't form hometask for check");
            throw new JspException("can't form hometask");
        }

        return SKIP_BODY;
    }
}
