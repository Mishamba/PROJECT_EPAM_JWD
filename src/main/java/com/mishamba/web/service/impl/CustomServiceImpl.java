package com.mishamba.web.service.impl;

import com.mishamba.web.dao.exception.DAOException;
import com.mishamba.web.dao.impl.DAOImpl;
import com.mishamba.web.model.Course;
import com.mishamba.web.model.ProgramStep;
import com.mishamba.web.service.CustomService;
import com.mishamba.web.service.exception.ServiceException;

import java.util.ArrayList;

public class CustomServiceImpl implements CustomService {

    private static class CustomServiceImplHolder {
        private static final CustomServiceImpl HOLDER = new CustomServiceImpl();
    }

    public static CustomServiceImpl getInstance() {
        return CustomServiceImplHolder.HOLDER;
    }

    @Override
    public String formCourseStringAnswer() throws ServiceException {
        ArrayList<Course> courses = null;
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
            answer.append("<h3>").append(course.getCourseName()).
                    append("</h3>");
            answer.append("<br><br>");
            answer.append("<h3>begin date</h3><br>");
            answer.append("<p>").append(course.getBeginOfCourse()).
                    append("</p>");
            answer.append("<br><br>");
            answer.append("<h3>end date</h3><br>");
            answer.append("<p>").append(course.getEndOfCourse()).
                    append("</p>");
            answer.append("<br><br>");
            answer.append("<h3>course teacher</h3><br>");
            if (course.getTeacher() != null) {
                answer.append("<p>").append(course.getTeacher().getFirstName()).
                        append(" ").append(course.getTeacher().getLastName()).
                        append("</p>");
            } else {
                answer.append("<p>need teacher!</p>");
            }
            answer.append("<br><br>");
            answer.append("<h3>max quantity of students</h3>");
            answer.append("<p>").append(course.getMaxStudentQuantity()).
                    append("</p>");
            answer.append("<h3>CourseProgram</h3><br>");
            if (course.getCourseProgram() != null) {
                for (ProgramStep programStep : course.getCourseProgram()) {
                    answer.append("<h4>step number  ").append(
                            programStep.getStep()).append("</h4><br>");
                    answer.append("<h4> step name  ").append(
                            programStep.getStepName()).append("</h4><br>");
                    if (programStep.getDescription() != null) {
                        answer.append("<h4>step Description  ").append(
                                programStep.getDescription()).append("</h4><br>");
                    } else {
                        answer.append("<p>no description</p><br>");
                    }
                    answer.append("<h4>step start date  ").append(
                            programStep.getStartDate()).append("</h4><br>");
                    answer.append("<h4>step end date  ").append(
                            programStep.getEndDate()).append("</h4><br>");
                }
            } else {
                answer.append("<p>no program right now</p>");
            }
            answer.append("<br><br>");
        }
        answer.append("<br>");

        return answer.toString();
    }
}
