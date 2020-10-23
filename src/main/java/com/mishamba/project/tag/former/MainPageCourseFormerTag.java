package com.mishamba.project.tag.former;

import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.util.parser.impl.DateParser;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MainPageCourseFormerTag extends TagSupport {
    private Course course;
    private int studentsOnCourseQuantity;

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudentsOnCourseQuantity(int studentsOnCourseQuantity) {
        this.studentsOnCourseQuantity = studentsOnCourseQuantity;
    }

    public int doStartTag() throws JspException {
        DateParser dateParser = new DateParser();
        try {
            JspWriter out = pageContext.getOut();

            out.write("<h3>Course name</h3><br>");
            out.write("<p>" + course.getCourseName() + "</p><br>");
            out.write("<h3>Begin course date</h3><br>");
            out.write("<p>"
                    + dateParser.parseDateToString(course.getBeginOfCourse())
                    + "</p><br>");
            out.write("<h3>End course date</h3><br>");
            out.write("<p>" + course.getEndOfCourse() + "</p><br>");
            out.write("<h3>Course teacher</h3><br>");
            if (course.getTeacher() != null) {
                out.write("<h4>First name</h4><br>");
                out.write("<p>" + course.getTeacher().getFirstName()
                        + "</p><br>");
                out.write("<h4>Last name</h4><br>");
                out.write("<p>" + course.getTeacher().getLastName()
                        + "</p><br>");
                out.write("<h4>Birthday</h4><br>");
                out.write("<p>" + course.getTeacher().getBirthday()
                        + "</p><br>");
            } else {
                out.write("<p>course has no teacher<p>");
            }
            out.write("<h3>Students on course</h3>");
            out.write("<p>" + studentsOnCourseQuantity + "/"
                    + course.getMaxStudentQuantity() + "</p><br>");
            if (course.getCourseProgram() != null) {
                for (ProgramStep programStep : course.getCourseProgram()) {
                    out.write("<h4>step number</h4><br>");
                    out.write("<p>" + programStep.getStep()
                            + "</p><br>");
                    out.write("<h4> step name</h4><br>");
                    out.write("<p>" + programStep.getStepName()
                            + "</p><br>");
                    if (programStep.getDescription() != null) {
                        out.write("<h4>step Description</h4><br>");
                        out.write("<p>" + programStep.getDescription()
                                + "</p><br>");
                    } else {
                        out.write("<p>no description</p><br>");
                    }
                    out.write("<h4>step start date</h4>");
                    out.write("<p>" + dateParser.
                            parseDateToString(programStep.getStartDate())
                            + "</p><br>");
                    out.write("<h4>step end date</h4><br>");
                    out.write("<p>" + dateParser.
                            parseDateToString(programStep.getEndDate())
                            + "</p><br>");
                }
            } else {
                out.write("<p>no program right now</p>");
            }
        } catch (IOException e) {
            throw new JspException("can't form ");
        }

        return SKIP_BODY;
    }
}
