package com.mishamba.project.service.impl;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.dao.impl.DAOImpl;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.service.CustomService;
import com.mishamba.project.service.exception.ServiceException;

import java.util.ArrayList;

public class CustomServiceImpl implements CustomService {

    private static class CustomServiceImplHolder {
        private static final CustomServiceImpl HOLDER = new CustomServiceImpl();
    }

    public static CustomServiceImpl getInstance() {
        return CustomServiceImplHolder.HOLDER;
    }

    @Override
    public String formMainCourses() throws ServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOImpl.getInstance().getCoursesWithoutTeacher();
        } catch (DAOException e) {
            throw new ServiceException("can't get courses without teacher");
        }
        for (Course course : courses) {
            try {
                course.setCourseProgram(DAOImpl.getInstance().
                        getCourseProgram(course));
            } catch (DAOException e) {
                throw new ServiceException("can't find course program", e);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (Course course : courses) {
            answer.append(formHtmlCourse(course));
        }
        answer.append("<br>");

        return answer.toString();
    }

    private StringBuilder formHtmlCourse(Course course) {
        StringBuilder courseString = new StringBuilder();
        courseString.append("<h3>").append(course.getCourseName()).
                append("</h3>");
        courseString.append("<br><br>");
        courseString.append("<h3>begin date</h3><br>");
        courseString.append("<p>").append(course.getBeginOfCourse()).
                append("</p>");
        courseString.append("<br><br>");
        courseString.append("<h3>end date</h3><br>");
        courseString.append("<p>").append(course.getEndOfCourse()).
                append("</p>");
        courseString.append("<br><br>");
        courseString.append("<h3>course teacher</h3><br>");
        if (course.getTeacher() != null) {
            courseString.append("<p>").append(course.getTeacher().getFirstName()).
                    append(" ").append(course.getTeacher().getLastName()).
                    append("</p>");
        } else {
            courseString.append("<p>need teacher!</p>");
        }
        courseString.append("<br><br>");
        courseString.append("<h3>max quantity of students</h3>");
        courseString.append("<p>").append(course.getMaxStudentQuantity()).
                append("</p>");
        courseString.append("<h3>CourseProgram</h3><br>");
        if (course.getCourseProgram() != null) {
            for (ProgramStep programStep : course.getCourseProgram()) {
                courseString.append("<h4>step number  ").append(
                        programStep.getStep()).append("</h4><br>");
                courseString.append("<h4> step name  ").append(
                        programStep.getStepName()).append("</h4><br>");
                if (programStep.getDescription() != null) {
                    courseString.append("<h4>step Description  ").append(
                            programStep.getDescription()).append("</h4><br>");
                } else {
                    courseString.append("<p>no description</p><br>");
                }
                courseString.append("<h4>step start date  ").append(
                        programStep.getStartDate()).append("</h4><br>");
                courseString.append("<h4>step end date  ").append(
                        programStep.getEndDate()).append("</h4><br>");
            }
        } else {
            courseString.append("<p>no program right now</p>");
        }
        courseString.append("<br><br>");

        return courseString;
    }

    @Override
    public String formCoursesCatalog() throws ServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOImpl.getInstance().getActiveCourses();
        } catch (DAOException e) {
            throw new ServiceException("can't get active courses", e);
        }

        StringBuilder answer = new StringBuilder();
        for (Course course : courses) {
            answer.append(formHtmlCourse(course));
        }

        return answer.toString();
    }
}
