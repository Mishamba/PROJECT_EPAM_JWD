package com.mishamba.project.tag.former;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.util.parser.impl.DateParser;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

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

        try {
            JspWriter out = pageContext.getOut();

            out.write("<br>");
            out.write("<h3>Course name</h3>");
            out.write("<br>");
            out.write(course.getCourseName());
            out.write("<br>");
            out.write("<h3>Begin course date</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(course.getBeginOfCourse()));
            out.write("<br>");
            out.write("<h3>End of course</h3>");
            out.write("<br>");
            out.write(dateParser.parseDateToString(course.getEndOfCourse()));
            out.write("<br>");
            out.write("<h3>Course teacher</h3>");
            out.write("<br>");
            if (course.getTeacher() != null) {
                out.write("<h4>Teacher first name</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getFirstName());
                out.write("<br>");
                out.write("<h4>Teacher last name</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getLastName());
                out.write("<br>");
                out.write("<h4>Teachers email</h4>");
                out.write("<br>");
                out.write(course.getTeacher().getEmail());
                out.write("<br>");
            } else {
                out.write("<p>no teacher on course now</p>");
                out.write("<br>");
            }
            out.write("<h3>Students on course quantity</h3>");
            out.write("<p>" + studentsOnCourseQuantity + "/" + course.getMaxStudentQuantity() + "</p>");
            out.write("<h3>Course program</h3>");
            out.write("<br>");
            for (ProgramStep step : course.getCourseProgram()) {
                out.write("<h4>Step name</h4>");
                out.write("<br>");
                out.write(step.getStepName());
                out.write("<br>");
                out.write("<h4>Step description<h4>");
                out.write("<br>");
                if (step.getDescription() != null) {
                    out.write(step.getDescription());
                } else {
                    out.write("<p>no description on this step</p>");
                }
                out.write("<br>");
                out.write("<h4>Step start date</h4>");
                out.write(dateParser.parseDateToString(step.getStartDate()));
                out.write("<br>");
                out.write("<h4>Step end date</h4>");
                out.write("<br>");
                out.write(dateParser.parseDateToString(step.getEndDate()));
                out.write("<br>");
            }
            if (course.getFinished()) {
                out.write("<h3>Course is finished</h3>");
            } else {
                out.write("<h3>Course is active</h3>");
            }
        } catch (IOException e) {
            logger.error("can't form course info");
            throw new JspException("can't form course info", e);
        }

        return SKIP_BODY;
    }
}
