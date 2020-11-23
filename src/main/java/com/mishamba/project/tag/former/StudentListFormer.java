package com.mishamba.project.tag.former;

import com.mishamba.project.model.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentListFormer extends TagSupport {
    private User student;
    private int courseId;

    public void setStudent(User student) {
        this.student = student;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>" + student.getFirstName() + "</h3>");
            out.write("<br>");
            out.write("<h3>" + student.getLastName() + "</h3>");
            out.write("<br>");
            out.write("<br>");
            out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"student_progress\">");
            out.write("<input type=\"hidden\" name=\"course_id\" value=\"");
            out.write(courseId);
            out.write("\">");
            out.write("<input type=\"hidden\" name=\"student_id\" value=\"");
            out.write(student.getId());
            out.write("\">");
            out.write("<input type=\"submit\" value=\"");
            out.write(resourceBundle.getString("view_progress_sign"));
            out.write("\">");
            out.write("</form><br>");
            out.write("<br>");
        } catch (IOException e) {
            throw new JspException("can't form student info", e);
        }

        return SKIP_BODY;
    }
}
