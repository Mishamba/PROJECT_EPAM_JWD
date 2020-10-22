package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.*;
import com.mishamba.project.service.CustomService;
import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.FormerProvider;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;
import com.mishamba.project.util.parser.DateParser;
import com.mishamba.project.util.validator.DateValidator;
import com.mishamba.project.util.validator.MarkValidator;
import org.apache.log4j.Logger;

import java.util.*;

public class CustomServiceImpl implements CustomService {
    private final Logger logger = Logger.getLogger(CustomServiceImpl.class);

    // TODO: 10/22/20 remove this redundant method
    private String getCourseAdd(Course course) {
        if (course == null) {
            return "no course to form given";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("<h3>Course name</h3><br>");
        builder.append("<p>").append(course.getCourseName()).append("</p><br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/course_profile\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"course_profile\">");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(course.getId()).append("\">");
        builder.append("<input type=\"submit\" value=\"Course Profile\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    // TODO: 10/22/20 remove this redundant method
    @Override
    public String formPageParameter(Properties properties) throws CustomServiceException {
        Former former;
        former = FormerProvider.getInstance().getFormer(properties);
        if (former == null) {
            throw new CustomServiceException("can't get such former");
        }

        return former.form(properties);
    }

    @Override
    public boolean isTeacherLeadsOrLeadedCourse(int teacherId, int courseId)
            throws CustomServiceException {
        try {
            ArrayList<Course> courses = DAOFactory.getInstance().
                    getCourseDAO().getTeacherManagedCourses(teacherId);
            courses.add(DAOFactory.getInstance().getCourseDAO().
                    getTeacherManageCourse(teacherId));
            for (Course course : courses) {
                if (course.getTeacher().getId() == teacherId) {
                    return true;
                }
            }
        } catch (DAOException e) {
            logger.error("can't get teachers course", e);
            throw new CustomServiceException("can't get teachers course", e);
        }

        return false;
    }

    // TODO: 10/22/20 redundant
    private String formHometaskForCheck(Hometask hometask) {
        StringBuilder builder = new StringBuilder();

        builder.append(formHometask(hometask));
        builder.append("<br>");
        builder.append("<h3>Answer</h3>");
        builder.append("<br>");
        builder.append(hometask.getResponse().getAnswer());
        builder.append("<br>");

        return builder.toString();
    }

    // TODO: 10/22/20 redundant 
    private String formUserForProgressPage(User user) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        builder.append("<h3>First name</h3>");
        builder.append("<br>");
        builder.append(user.getFirstName());
        builder.append("<br>");
        builder.append("<h3>Last name</h3>");
        builder.append("<br>");
        builder.append(user.getLastName());
        builder.append("<h3>Birthday</h3>");
        builder.append("<br>");
        builder.append(user.getBirthday());
        builder.append("<br>");
        builder.append("<h3>Email</h3>");
        builder.append("<br>");
        builder.append(user.getEmail());
        builder.append("<br>");

        return builder.toString();
    }

    // TODO: 10/22/20 redundant 
    private String formHometask(Hometask hometask) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<h3>").append("Hometask title").append("</h3>");
        builder.append("<br>");
        builder.append(hometask.getTitle());
        builder.append("<br>");
        builder.append("<h3>").append("Hometask description").append("</h3>");
        builder.append("<br>");
        builder.append(hometask.getDescription());
        builder.append("<br>");
        builder.append("<h3>").append("Deadline").append("</h3>");
        builder.append(hometask.getDeadline());
        builder.append("<br>");

        return builder.toString();
    }

    private String formHometaskForList(Hometask hometask, String role) {
        StringBuilder builder = new StringBuilder();

        builder.append(formHometask(hometask));
        builder.append(formHometaskResponce(hometask, role));

        return builder.toString();
    }

    private String formHometaskResponce(Hometask hometask, String role) {
        if (hometask == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        if (hometask.getResponse() != null && role.equals("student")) {
            builder.append("<h3>Your answer</h3>");
            builder.append("<br>");
            builder.append(hometask.getResponse().getAnswer());
            builder.append("<br>");
            builder.append("<h3>Mark</h3>");
            builder.append("<br>");
            int mark = hometask.getResponse().getMark();
            builder.append((mark == 0) ? "no mark given" : mark);
            builder.append("<br>");
        } else if (hometask.getResponse() == null && role.equals("student")) {
            builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                    formAnswerHometaskButton(hometask.getId()));
        } else if (hometask.getResponse() != null && role.equals("teacher")) {
            builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                    formCheckHometaskButton(hometask.getId(),
                            hometask.getResponse().getStudentId()));
        }

        return builder.toString();
    }

    private String formStudentForList(User student, int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<h3>").append(student.getFirstName()).append("</h3>");
        builder.append("<br>");
        builder.append("<h3>").append(student.getLastName()).append("</h3>");
        builder.append("<br>");
        builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                formStudentProgressButton(courseId, student.getId()));
        builder.append("<br>");

        return builder.toString();
    }

    private String formUserCourseList(Course course) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<h3>").append(course.getCourseName()).append("</h3>");
        builder.append("<br>");
        builder.append("<h3>").append(PartsBuilderFactory.getInstance().
                getPartsBuilder().formViewCourseInfoButton(course.getId()));
        builder.append("<br>");

        return builder.toString();
    }

    private String formHtmlCourse(Course course) throws CustomServiceException {
        Integer studentsOnCourseQuantity;
        try {
            studentsOnCourseQuantity = DAOFactory.getInstance().getCourseDAO().
                    getStudentsOnCourseQuantity(course);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get course info", e);
        }

        StringBuilder builder = new StringBuilder();
        DateParser dateParser = new DateParser();

        builder.append("<h3>Course name</h3><br>");
        builder.append("<p>").append(course.getCourseName()).append("</p><br>");
        builder.append("<h3>Begin course date</h3><br>");
        builder.append("<p>").
                append(dateParser.parseDateToString(course.getBeginOfCourse())).
                append("</p><br>");
        builder.append("<h3>End course date</h3><br>");
        builder.append("<p>").append(course.getEndOfCourse()).append("</p><br>");
        builder.append("<h3>Course teacher</h3><br>");
        if (course.getTeacher() != null) {
            builder.append("<h4>First name</h4><br>");
            builder.append("<p>").append(course.getTeacher().getFirstName()).
                    append("</p><br>");
            builder.append("<h4>Last name</h4><br>");
            builder.append("<p>").append(course.getTeacher().getLastName()).
                    append("</p><br>");
            builder.append("<h4>Birthday</h4><br>");
            builder.append("<p>").append(course.getTeacher().getBirthday()).
                    append("</p><br>");
        } else {
            builder.append("<p>course has no teacher<p>");
        }
        builder.append("<h3>Students on course</h3>");
        builder.append("<p>").append(studentsOnCourseQuantity).append("/").
                append(course.getMaxStudentQuantity()).append("</p><br>");
        if (course.getCourseProgram() != null) {
            for (ProgramStep programStep : course.getCourseProgram()) {
                builder.append("<h4>step number</h4><br>");
                builder.append("<p>").append(programStep.getStep()).
                        append("</p><br>");
                builder.append("<h4> step name</h4><br>");
                builder.append("<p>").append(programStep.getStepName()).
                        append("</p><br>");
                if (programStep.getDescription() != null) {
                    builder.append("<h4>step Description</h4><br>");
                    builder.append("<p>").append(programStep.getDescription()).
                            append("</p><br>");
                } else {
                    builder.append("<p>no description</p><br>");
                }
                builder.append("<h4>step start date</h4>");
                builder.append("<p>").append(dateParser.
                        parseDateToString(programStep.getStartDate())).
                        append("</p><br>");
                builder.append("<h4>step end date</h4><br>");
                builder.append("<p>").append(dateParser.
                        parseDateToString(programStep.getEndDate())).
                        append("</p><br>");
            }
        } else {
            builder.append("<p>no program right now</p>");
        }

        return builder.toString();
    }
}
