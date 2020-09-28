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
            course.setCourseProgram(DAOImpl.getInstance().
                    getCourseProgram(course));
        }

        StringBuilder answer = new StringBuilder();
        for (Course course : courses) {
            answer.append("<h2>").append(course.getCourseName()).
                    append("</h2>");
            answer.append("<br><br>");
            answer.append("<p>begin date</p><br>");
            answer.append("<p>").append(course.getBeginOfCourse()).
                    append("</p>");
            answer.append("<br><br>");
            answer.append("<p>end date</p><br>");
            answer.append("<p>").append(course.getEndOfCourse()).
                    append("</p>");
            answer.append("<br><br>");
            answer.append("<p>course teacher<p><br>");
            answer.append("<p>").append(course.getTeacher().getFirstName()).
                    append(" ").append(course.getTeacher().getLastName()).
                    append("</p>");
            answer.append("<br><br>");
            answer.append("<p>max quantity of students</p>");
            answer.append("<p>").append(course.getMaxStudentQuantity()).
                    append("</p>");
            answer.append("<p>CourseProgram</p><br>");
            for (ProgramStep programStep : course.getCourseProgram()) {
                answer.append("<p>step number  ").append(
                        programStep.getStep()).append("</p><br>");
                answer.append("<p> step name  ").append(
                        programStep.getStepName()).append("</p><br>");
                answer.append("<p>step Description  ").append(
                        programStep.getDescription()).append("</p><br>");
                answer.append("<p>step start date  ").append(
                        programStep.getStartDate()).append("</p><br>");
                answer.append("<p>step end date  ").append(
                        programStep.getEndDate()).append("</p><br>");
            }
        }

        return answer.toString();
    }
}
