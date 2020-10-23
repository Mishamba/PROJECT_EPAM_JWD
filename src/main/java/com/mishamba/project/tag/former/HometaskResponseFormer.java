package com.mishamba.project.tag.former;

import com.mishamba.project.model.Hometask;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HometaskResponseFormer extends TagSupport {
    private static final Logger logger = Logger.
            getLogger(HometaskResponseFormer.class);
    private Hometask hometask;
    private String role;

    public void setHometask(Hometask hometask) {
        this.hometask = hometask;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if (hometask.getResponse() != null && role.equals("student")) {
                out.write("<h3>Your answer</h3>");
                out.write("<br>");
                out.write(hometask.getResponse().getAnswer());
                out.write("<br>");
                out.write("<h3>Mark</h3>");
                out.write("<br>");
                int mark = hometask.getResponse().getMark();
                out.write((mark == 0) ? "no mark given" : String.valueOf(mark));
                out.write("<br>");
            } else if (hometask.getResponse() == null && role.equals("student")) {
                out.write("<br>");
                out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
                out.write("<input type=\"hidden\" name=\"command\" " +
                        "value=\"answer_hometask\">");
                out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"");
                out.write(hometask.getId());
                out.write("\">");
                out.write("<input type=\"submit\" value=\"Answer hometask\">");
            } else if (hometask.getResponse() != null && role.equals("teacher")) {
                out.write("<br>");
                out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
                out.write("<input type=\"hidden\" name=\"command\" " +
                        "value=\"check_hometask\">");
                out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"");
                out.write(hometask.getId());
                out.write("\">");
                out.write("<input type=\"hidden\" name=\"student_id\" value=\"");
                out.write(hometask.getResponse().getStudentId());
                out.write("\">");
                out.write("<input type=\"submit\" value=\"Check hometask\">");
                out.write("</form><br>");
            }
        } catch (IOException e) {
            logger.error("can't form hometask response");
            throw new JspException("can't form hometask response", e);
        }

        return SKIP_BODY;
    }
}
