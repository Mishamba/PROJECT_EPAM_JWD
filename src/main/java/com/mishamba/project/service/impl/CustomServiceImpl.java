package com.mishamba.project.service.impl;

import com.mishamba.project.dao.DAOFactory;
import com.mishamba.project.dao.exception.DAOException;
import com.mishamba.project.model.Course;
import com.mishamba.project.model.Hometask;
import com.mishamba.project.model.ProgramStep;
import com.mishamba.project.model.User;
import com.mishamba.project.service.CustomService;
import com.mishamba.project.service.exception.CustomServiceException;
import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.service.former.builder.Former;
import com.mishamba.project.service.former.FormerProvider;
import com.mishamba.project.service.former.factory.PartsBuilderFactory;
import com.mishamba.project.util.parser.DateParser;
import com.mishamba.project.util.validator.DateValidator;
import org.apache.log4j.Logger;

import java.util.*;

public class CustomServiceImpl implements CustomService {
    private final Logger logger = Logger.getRootLogger();
    private final String NO_HOMETASK_SIGN = "<p>no hometask on this course</p>";

    @Override
    public String getCourseHometaskForUser(int courseId, int userId, String role) throws CustomServiceException {
        ArrayList<Hometask> hometasks;
        try {
            logger.info("getting hometask on course");
            hometasks = DAOFactory.getInstance().getHometaskDAO().
                    getHometasksOnCourseForUser(courseId, userId);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get course hometask", e);
        }

        if (hometasks.isEmpty()) {
            return NO_HOMETASK_SIGN;
        }

        StringBuilder hometasksList = new StringBuilder();

        for (Hometask hometask : hometasks) {
            hometasksList.append(formHometaskForList(hometask, role));
        }

        return hometasksList.toString();
    }

    @Override
    public String getMainCourses() throws CustomServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOFactory.getInstance().getCourseDAO().getCoursesWithoutTeacher();
        } catch (DAOException e) {
            throw new CustomServiceException("can't get courses without teacher");
        }
        for (Course course : courses) {
            try {
                course.setCourseProgram(DAOFactory.getInstance().
                        getProgramStepDAO().getCourseProgram(course));
            } catch (DAOException e) {
                throw new CustomServiceException("can't find course program", e);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (Course course : courses) {
            answer.append(getCourseAdd(course));
        }
        answer.append("<br>");

        return answer.toString();
    }

    @Override
    public String getCoursesCatalog() throws CustomServiceException {
        ArrayList<Course> courses;
        try {
            courses = DAOFactory.getInstance().getCourseDAO().getActiveCourses();
        } catch (DAOException e) {
            throw new CustomServiceException("can't get active courses", e);
        }

        StringBuilder answer = new StringBuilder();
        for (Course course : courses) {
            answer.append(getCourseAdd(course));
        }

        return answer.toString();
    }

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
    public boolean checkSignInData(String email, String password) throws CustomServiceException {
        int passwordHash = password.hashCode();
        passwordHash = Integer.valueOf(passwordHash).hashCode();

        try {
            return DAOFactory.getInstance().getUserDAO().
                    checkSignInData(email, passwordHash);
        } catch (DAOException e) {
            throw new CustomServiceException("can't check sign in data", e);
        }
    }

    @Override
    public Properties getUserByEmail(String email) throws CustomServiceException {
        Properties userInfo = new Properties();
        DateParser dateParser = new DateParser();

        try {
            User user = DAOFactory.getInstance().getUserDAO().
                    getUserByEmail(email);
            userInfo.setProperty("id", user.getId().toString());
            userInfo.setProperty("firstName", user.getFirstName());
            userInfo.setProperty("lastName", user.getLastName());
            userInfo.setProperty("email", user.getEmail());
            userInfo.setProperty("birthday", dateParser.parseDateToString(
                    user.getBirthday()));
            userInfo.setProperty("role", user.getRole());
            return userInfo;
        } catch (DAOException e) {
            throw new CustomServiceException("can't get first name", e);
        }
    }

    @Override
    public boolean createUser(Properties userInfo) throws CustomServiceException {
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
            throw new CustomServiceException("date entered incorrect", e);
        }
        String role = userInfo.getProperty("role");

        DateValidator dateValidator = new DateValidator();
        if (dateValidator.checkForFuture(birthday)) {
            User newUser = new User(null, firstName, lastName, email, birthday, null,
                    role);
            try {
                return DAOFactory.getInstance().getUserDAO().createUser(newUser, passwordHash);
            } catch (DAOException e) {
                throw new CustomServiceException("can't sign up new user", e);
            }
        } else {
            return false;
        }
    }

    @Override
    public String getCourseProfile(int courseId) throws CustomServiceException {
        Course course;
        try {
            course = DAOFactory.getInstance().getCourseDAO().
                    getCourseById(courseId);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get course info", e);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(formHtmlCourse(course));

        if (course.getFinished()) {
            builder.append("<h3>Course is active</h3><br>");
        } else {
            builder.append("<h3>Course is finished</h3><br>");
        }

        return builder.toString();
    }

    @Override
    public void enterStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException {
        try {
            DAOFactory.getInstance().getCourseDAO().
                    enterStudentOnCourse(studentId, courseId);
        } catch (DAOException e) {
            logger.error("can't enter student on course");
            throw new CustomServiceException("can't enter student on course", e);
        }
    }

    @Override
    public int getUserIdByEmail(String email) throws CustomServiceException {
        try {
            return DAOFactory.getInstance().getUserDAO().getUserIdByEmail(email);
        } catch (DAOException e) {
            logger.error("can't get user id");
            throw new CustomServiceException("can't get user id", e);
        }
    }

    @Override
    public String getUserCourses(Properties properties) throws CustomServiceException {
        boolean finished = Boolean.parseBoolean((String) properties.get("finished"));
        int userId = Integer.parseInt((String) properties.get("userId"));
        String role = properties.getProperty("role");

        List<Course> courses;
        try {
            courses = (role.equals("student")) ? DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(userId, finished) :
                    (finished) ? DAOFactory.getInstance().getCourseDAO().
                            getTeacherManagedCourses(userId) :
                            Collections.singletonList(DAOFactory.getInstance().
                                    getCourseDAO().getTeacherManageCourse(userId));
        } catch (DAOException e) {
            throw new CustomServiceException("can't get courses", e);
        }

        StringBuilder builder = new StringBuilder();

        for (Course course : courses) {
            if (course != null) {
                builder.append(formUserCourseList(course));
            } else {
                builder.append("<p>U not managing any courses now</p>");
            }
        }

        return builder.toString();
    }

    @Override
    public boolean isStudentOnCourse(int studentId, int courseId)
            throws CustomServiceException {
        try {
            ArrayList<Course> usersCourses = DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(studentId, false);
            ArrayList<Course> usersPassedCourse = DAOFactory.getInstance().
                    getCourseDAO().getStudentsCourses(studentId, true);
            usersCourses.addAll(usersPassedCourse);
            for (Course course : usersCourses) {
                if (course.getId() == courseId) {
                    return true;
                }
            }
        } catch (DAOException e) {
            throw new CustomServiceException("can't check is student on course", e);
        }

        return false;
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

    @Override
    public Properties getCourseById(int courseId) throws CustomServiceException {
        try {
            Course course = DAOFactory.getInstance().getCourseDAO().getCourseById(courseId);
            DateParser dateParser = new DateParser();

            Properties courseInfo = new Properties();
            courseInfo.setProperty("id", String.valueOf(course.getId()));
            courseInfo.setProperty("courseName", course.getCourseName());
            courseInfo.setProperty("beginOfCourse", dateParser.
                    parseDateToString(course.getBeginOfCourse()));
            courseInfo.setProperty("endOfCourse", dateParser.
                    parseDateToString(course.getEndOfCourse()));
            courseInfo.setProperty("teacherId", String.valueOf(course.getTeacher().getId()));
            courseInfo.setProperty("teacherFirstName", course.getTeacher().getFirstName());
            courseInfo.setProperty("teacherLastName", course.getTeacher().getLastName());
            courseInfo.setProperty("teacherEmail", course.getTeacher().getEmail());
            courseInfo.setProperty("maxStudentsQuantity", String.valueOf(course.getMaxStudentQuantity()));
            courseInfo.setProperty("finished", String.valueOf(course.getFinished()));

            return courseInfo;
        } catch (DAOException e) {
            logger.error("can't get course");
            throw new CustomServiceException("can't get course", e);
        }
    }

    @Override
    public String getStudentsOnCourse(int courseId) throws CustomServiceException {
        StringBuilder builder = new StringBuilder();

        try {
            ArrayList<User> students = DAOFactory.getInstance().getUserDAO().
                    getStudentsOnCourse(courseId);
            for (User student : students) {
                builder.append(formStudentForList(student, courseId));
            }
        } catch (DAOException e) {
            logger.error("can't get students on course");
        }

        return builder.toString();
    }

    @Override
    public void createHometask(Properties hometaskProperties) throws CustomServiceException {
        int courseId = Integer.parseInt(hometaskProperties.getProperty("courseId"));
        String title = hometaskProperties.getProperty("title");
        String description = hometaskProperties.getProperty("description");
        String deadlineNotParsed = hometaskProperties.getProperty("deadline");

        DateParser dateParser = new DateParser();
        DateValidator dateValidator = new DateValidator();

        try {
            if (DAOFactory.getInstance().getCourseDAO().getCourseById(courseId).getFinished()) {
                throw new CustomServiceException("can't create hometask. course is finished");
            }
        } catch (DAOException e) {
            logger.error("can't check is course finished");
            return;
        }

        Date deadline;
        try {
            deadline = dateParser.parseDate(deadlineNotParsed);
            if (dateValidator.checkForFuture(deadline)) {
                throw new CustomServiceException("deadline is not in future");
            }
        } catch (UtilException e) {
            throw new CustomServiceException("can't parse date", e);
        }
        Date beginDate = new Date();

        Hometask hometask = new Hometask(courseId, null, title, description,
                beginDate, deadline, null);
        try {
            DAOFactory.getInstance().getHometaskDAO().createHometask(hometask);
        } catch (DAOException e) {
            logger.error("can't create hometask");
            throw new CustomServiceException("can't create hometask", e);
        }
    }

    @Override
    public String getHometaskById(int hometaskId) throws CustomServiceException {
        try {
            Hometask hometask = DAOFactory.getInstance().getHometaskDAO().getHometaskById(hometaskId);

            return formHometask(hometask);
        } catch (DAOException e) {
            throw new CustomServiceException("can't get hometask", e);
        }
    }

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

        if (hometask.getResponse() != null && role.equals("student")) {
            builder.append("<h3>Your answer</h3>");
            builder.append("<br>");
            builder.append(hometask.getResponse().getAnswer());
            builder.append("<br>");
            builder.append("<h3>Mark</h3>");
            builder.append("<br>");
            builder.append(hometask.getResponse().getMark());
            builder.append("<br>");
        } else {
            builder.append(PartsBuilderFactory.getInstance().getPartsBuilder().
                    formAnswerHometaskButton(hometask.getId()));
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
