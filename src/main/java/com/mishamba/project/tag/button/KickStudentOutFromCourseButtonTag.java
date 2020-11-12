package com.mishamba.project.tag.button;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

public class KickStudentOutFromCourseButtonTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(KickStudentOutFromCourseButtonTag.class);

    private int courseId;
    private int studentId;
    private ResourceBundle resourceBundle;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"kick_student_page\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=\"");
            out.write(courseId);
            out.write("\">");
            out.write("<input type=\"hidden\" name=\"student_id\" value=\"");
            out.write(studentId);
            out.write("\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("kick_student_out_sign"));
            out.write("\">");
            out.write("</form><br>");
        } catch (IOException e) {
            logger.error("can't form button");
            throw new JspException("can' form button", e);
        }

        return SKIP_BODY;
    }
}
