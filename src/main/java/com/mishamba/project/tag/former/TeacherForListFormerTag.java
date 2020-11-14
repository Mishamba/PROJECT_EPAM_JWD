package com.mishamba.project.tag.former;

import com.mishamba.project.model.User;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TeacherForListFormerTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(TeacherForListFormerTag.class);

    private User teacher;

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sings/sing", locale);

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("first_name_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(teacher.getFirstName());
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("last_name_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(teacher.getLastName());
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("birthday_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(teacher.getBirthday()));
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("teacher_skills_sign"));
            out.write("</h3>");
            out.write("<br>");
            for (String skill : teacher.getTeacherSkills()) {
                out.write(skill);
                out.write("<br>");
            }
            out.write("<br>");
        } catch (IOException e) {
            logger.error("can't form teacher for list");
        }

        return SKIP_BODY;
    }
}
