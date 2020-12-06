package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.HometaskResponse;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HometaskAnswerFormer extends TagSupport {
    private static final Logger logger = Logger.getLogger(HometaskAnswerFormer.class);
    private HometaskResponse hometaskResponse;

    public void setHometaskResponse(HometaskResponse hometaskResponse) {
        this.hometaskResponse = hometaskResponse;
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);
        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("answer_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(hometaskResponse.getAnswer());
            out.write("<br>");
        } catch (IOException e) {
            logger.error("can't form hometask for check");
            throw new JspException("can't form hometask");
        }

        return SKIP_BODY;
    }
}
