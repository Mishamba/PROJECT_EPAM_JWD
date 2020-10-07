package com.mishamba.project.util.former.builder;

import com.mishamba.project.util.exception.UtilException;

import java.util.Properties;

public class PartsBuilder {
    public String formCoursesCatalogButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<form action =\"/PROJECT_EPAM_JWD_war/courses_catalog\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"courses_catalog\">");
        builder.append("<input type=\"submit\" value=\"Courses catalog\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formMainPageButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"main_page\">");
        builder.append("<input type=\"submit\" value=\"Main page\">");
        builder.append("</form>");

        return builder.toString();
    }

    public String formSingInButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<h2>Sign In</h2>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/sing_in\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"sing_in\">");
        builder.append("<input type=\"submit\" value=\"Sign in\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formSingUpButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<h2>Sign Up<h2>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/sing_up\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"sing_up\">");
        builder.append("<input type=\"submit\" value=\"Sing up\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formUserInfo(Properties properties) throws UtilException {
        if (properties == null) {
            throw new UtilException("given session is null");
        }
        StringBuilder builder = new StringBuilder();

        builder.append("<p>Role</p><br>");
        builder.append("<p>").
                append((String) properties.get("role")).
                append("</p><br>");
        builder.append("<p>First name</p><br>");
        builder.append("<p>").
                append((String) properties.get("firstName")).
                append("</p><br>");
        builder.append("<p>Second name</p><br>");
        builder.append("<p>").
                append((String) properties.get("lastName")).
                append("</p><br>");

        return builder.toString();
    }

    public String formSingOutButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<br><form action=\"/PROJECT_EPAM_JWD_war/sing_out\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"sing_out\">");
        builder.append("<input type=\"submit\" value=\"Sing Out\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formFinishCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action = \"/PROJECT_EPAM_JWD_war/course_profile\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"finish_course\">");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        builder.append("<input type=\"submit\" value = \"Finish course\">");
        builder.append("</form>");
        builder.append("<br>");

        return builder.toString();
    }

    public String formEnterTeacherOnCourse() {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<p>Enter teacher on course button</p>");
        builder.append("<br>"); // TODO: 10/6/20 finish this shit
        builder.append("<form ");
        return builder.toString();
    }

    public String formStudentSingUpForCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"student_sing_up_for_course\">");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=").
                append(courseId).append(">");
        builder.append("<input type=\"submit\" value=\"Sing up for course\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formTeacherManageCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"teacher_manage_course\">");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=").
                append(courseId).append(">");
        builder.append("<input type=\"submit\" name=\"Manage course\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formCreateUserButton() {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"sing_up\">");
        builder.append("<input type=\"submit\" value=\"Sing up someone\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formRemoveTeacherFromCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" value=\"remove_teacher_from_course\">");
        builder.append("<input type=\"submit\" value=\"Remove teacher from course\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formAppendTeacherOnCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"append_teacher_on_course>");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        builder.append("<input type=\"submit\" value=\"Append teacher on course\">");
        builder.append("</form><br>");

        return builder.toString();
    }

    public String formCourseHometasksButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"course_hometask\">");
        builder.append("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        builder.append("<input type=\"submit\" value=\"View hometask\">");

        return builder.toString();
    }

    public String formAnswerHometaskButton(int hometaskId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"answer_hometask\">");
        builder.append("<input type=\"hidden\" name=\"hometask_id\" value=\"").
                append(hometaskId).append("\">");
        builder.append("<input type=\"submit\" value=\"Answer hometask\">");

        return builder.toString();
    }

    public String formCheckHometaskButton(int hometaskId, int studentId) {
        StringBuilder builder = new StringBuilder();

        builder.append("<br>");
        builder.append("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        builder.append("<input type=\"hidden\" name=\"command\" " +
                "value=\"check_hometask\">");
        builder.append("<input type=\"hidden\" name=\"hometask_id\" value=\"").
                append(hometaskId).append("\">");
        builder.append("<input type=\"hidden\" name=\"student_id\" value=\"").
                append(studentId).append("\">");
        builder.append("</form><br>");

        return builder.toString();
    }
}
