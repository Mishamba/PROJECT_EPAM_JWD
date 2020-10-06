package com.mishamba.project.service.impl;

import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.dao.impl.DAOImpl;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.model.User;
import com.mishamba.project.service.CustomService;
import com.mishamba.project.service.exception.ServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.former.builder.Former;
import com.mishamba.project.util.former.FormerProvider;
import com.mishamba.project.util.parser.DateParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

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
            answer.append(formCourseAdd(course));
        }
        answer.append("<br>");

        return answer.toString();
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
            answer.append(formCourseAdd(course));
        }

        return answer.toString();
    }

    private String formCourseAdd(Course course) {
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

    @Override
    public String formPageParameter(Properties properties) throws ServiceException {
        Former former;
        try {
            former = FormerProvider.getInstance().getFormer(properties);
            if (former == null) {
                throw new ServiceException("can't get such former");
            }

            return former.form(properties);
        } catch (UtilException e) {
            throw new ServiceException("can't get page parameters", e);
        }
    }

    @Override
    public boolean checkSingInData(String email, String password) throws ServiceException {
        int passwordHash = password.hashCode();
        passwordHash = Integer.valueOf(passwordHash).hashCode();

        try {
            return DAOImpl.getInstance().checkSingInData(email, passwordHash);
        } catch (DAOException e) {
            throw new ServiceException("can't check sing in data", e);
        }
    }

    @Override
    public Properties getUserByEmail(String email) throws ServiceException {
        Properties userInfo = new Properties();
        try {
            User user = DAOImpl.getInstance().getUserByEmail(email);
            userInfo.setProperty("id", user.getId().toString());
            userInfo.setProperty("firstName", user.getFirstName());
            userInfo.setProperty("lastName", user.getLastName());
            userInfo.setProperty("email", user.getEmail());
            userInfo.setProperty("birthday", user.getBirthday().toString());
            userInfo.setProperty("role", user.getRole());
            return userInfo;
        } catch (DAOException e) {
            throw new ServiceException("can't get first name", e);
        }
    }

    @Override
    public boolean createUser(Properties userInfo) throws ServiceException {
        String firstName = userInfo.getProperty("firstName");
        String lastName = userInfo.getProperty("lastName");
        String email = userInfo.getProperty("email");
        String birthdayDate = userInfo.getProperty("birthday");
        String password = userInfo.getProperty("password");
        int passwordHash = password.hashCode();
        passwordHash = Integer.valueOf(passwordHash).hashCode();
        Date birthday;
        try {
            birthday = new DateParser().parseDate(birthdayDate);
        } catch (UtilException e) {
            throw new ServiceException("date entered incorrect", e);
        }
        String role = userInfo.getProperty("role");

        User newUser = new User(null, firstName, lastName, email, birthday,
                role);
        try {
            return DAOImpl.getInstance().createUser(newUser, passwordHash);
        } catch (DAOException e) {
            throw new ServiceException("can't sing up new user", e);
        }
    }

    @Override
    public String formCourseProfile(int courseId) throws ServiceException {
        Course course;
        try {
            course= DAOImpl.getInstance().getCourseById(courseId);
        } catch (DAOException e) {
            throw new ServiceException("can't get course info", e);
        }

        StringBuilder builder = formHtmlCourse(course);

        if (course.getFinished()) {
            builder.append("<h3>Course is active</h3><br>");
        } else {
            builder.append("<h3>Course is finished</h3><br>");
        }

        return builder.toString();
    }

    private StringBuilder formHtmlCourse(Course course) throws ServiceException {
        Integer studentsOnCourseQuantity;
        try {
            studentsOnCourseQuantity = DAOImpl.getInstance().getStudentsOnCourseQuantity(course);
        } catch (DAOException e) {
            throw new ServiceException("can't get course info", e);
        }

        StringBuilder builder = new StringBuilder();

        builder.append("<h3>Course name</h3><br>");
        builder.append("<p>").append(course.getCourseName()).append("</p><br>");
        builder.append("<h3>Begin course date</h3><br>");
        builder.append("<p>").append(course.getBeginOfCourse()).append("</p><br>");
        builder.append("<h3>End course date</h3><br>");
        builder.append("<p>").append(course.getEndOfCourse()).append("</p><br>");
        builder.append("<h3>Course teacher</h3><br>");
        builder.append("<h4>First name</h4><br>");
        builder.append("<p>").append(course.getTeacher().getFirstName()).
                append("</p><br>");
        builder.append("<h4>Last name</h4><br>");
        builder.append("<p>").append(course.getTeacher().getLastName()).
                append("</p><br>");
        builder.append("<h4>Birthday</h4><br>");
        builder.append("<p>").append(course.getTeacher().getBirthday()).
                append("</p><br>");
        builder.append("<h3>Students on course</h3>");
        builder.append("<p>").append(studentsOnCourseQuantity).append("/").
                append(course.getMaxStudentQuantity()).append("</p><br>");
        if (course.getCourseProgram() != null) {
            for (ProgramStep programStep : course.getCourseProgram()) {
                builder.append("<h4>step number  ").append(
                        programStep.getStep()).append("</h4><br>");
                builder.append("<h4> step name  ").append(
                        programStep.getStepName()).append("</h4><br>");
                if (programStep.getDescription() != null) {
                    builder.append("<h4>step Description  ").append(
                            programStep.getDescription()).append("</h4><br>");
                } else {
                    builder.append("<p>no description</p><br>");
                }
                builder.append("<h4>step start date  ").append(
                        programStep.getStartDate()).append("</h4><br>");
                builder.append("<h4>step end date  ").append(
                        programStep.getEndDate()).append("</h4><br>");
            }
        } else {
            builder.append("<p>no program right now</p>");
        }

        return builder;
    }
}
