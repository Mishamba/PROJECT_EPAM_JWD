package com.mishamba.project.tag.former;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CourseInfoFormerTag extends TagSupport {
    private static final Logger logger = Logger.getLogger(CourseInfoFormerTag.class);

    private Course course;
    private int studentsOnCourseQuantity;

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudentsOnCourseQuantity(int studentsQuantity) {
        this.studentsOnCourseQuantity = studentsQuantity;
    }

    @Override
    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();
        Locale locale = (Locale) pageContext.getSession().getAttribute("locale");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("signs/sign", locale);

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("course_name_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(course.getCourseName());
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("begin_of_course_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(course.getBeginOfCourse()));
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("end_of_course_sign"));
            out.write("</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(course.getEndOfCourse()));
            out.write("<br>");
            out.write("<h3>");
            out.write(resourceBundle.getString("course_teacher_sign"));
            out.write("</h3>");
            out.write("<br>");
            if (course.getTeacher() != null) {
                out.write("<h4>");
                out.write(resourceBundle.getString("teacher_first_name_sign"));
                out.write("</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getFirstName());
                out.write("<br>");
                out.write("<h4>");
                out.write(resourceBundle.getString("teacher_last_name_sign"));
                out.write("</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getLastName());
                out.write("<br>");
                out.write("<h4>");
                out.write(resourceBundle.getString("teachers_email_sign"));
                out.write("</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getEmail());
                out.write("<br>");
            } else {
                out.write("<p>");
                out.write(resourceBundle.getString("no_teacher_on_course_now_sign"));
                out.write("</p>");
                out.write("<br>");
            }
            out.write("<h3>");
            out.write(resourceBundle.getString("students_on_course_quantity_sign"));
            out.write("</h3>");
            out.write("<p>" + studentsOnCourseQuantity + "/" + course.getMaxStudentQuantity() + "</p>");
            out.write("<h3>");
            out.write(resourceBundle.getString("course_program_sign"));
            out.write("</h3>");
            out.write("<br>");
            for (ProgramStep step : course.getCourseProgram()) {
                out.write("<h4>");
                out.write(resourceBundle.getString("step_name_sign"));
                out.write("</h4>");
                out.write("<br>");
                out.write(step.getStepName());
                out.write("<br>");
                out.write("<h4>");
                out.write(resourceBundle.getString("step_description_sign"));
                out.write("<h4>");
                out.write("<br>");
                if (step.getDescription() != null) {
                    out.write(step.getDescription());
                } else {
                    out.write("<p>");
                    out.write(resourceBundle.getString("no_description_on_this_step_sign"));
                    out.write("</p>");
                }
                out.write("<br>");
                out.write("<h4>");
                out.write(resourceBundle.getString("step_start_date_sign"));
                out.write("</h4>");
                out.write(dateParser.parseDateToString(step.getStartDate()));
                out.write("<br>");
                out.write("<h4>");
                out.write(resourceBundle.getString("step_end_date_sign"));
                out.write("</h4>");
                out.write("<br>");
                out.write(dateParser.parseDateToString(step.getEndDate()));
                out.write("<br>");
            }
            if (course.getFinished()) {
                out.write("<h3>");
                out.write(resourceBundle.getString("course_is_finished_sign"));
                out.write("</h3>");
            } else {
                out.write("<h3>");
                out.write(resourceBundle.getString("course_is_active_sign"));
                out.write("</h3>");
            }
        } catch (IOException e) {
            logger.error("can't form course info");
            throw new JspException("can't form course info", e);
        }

        return SKIP_BODY;
    }
}
