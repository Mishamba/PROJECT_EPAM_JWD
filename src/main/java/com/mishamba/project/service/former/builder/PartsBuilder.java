package com.mishamba.project.service.former.builder;

import com.mishamba.project.service.exception.CustomServiceException;

import java.util.Properties;

public class PartsBuilder {
    public String formCoursesCatalogButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<form action =\"/PROJECT_EPAM_JWD_war/courses_catalog\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"courses_catalog\">");
        out.write("<input type=\"submit\" value=\"Courses catalog\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formMainPageButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"main_page\">");
        out.write("<input type=\"submit\" value=\"Main page\">");
        out.write("</form>");

        return builder.toString();
    }

    public String formSignInButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<h2>Sign In</h2>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/sign_in\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"sign_in\">");
        out.write("<input type=\"submit\" value=\"Sign in\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formSignUpButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<h2>Sign Up<h2>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/sign_up\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"sign_up\">");
        out.write("<input type=\"submit\" value=\"Sign up\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formUserInfo(Properties properties) throws CustomServiceException {
        if (properties == null) {
            throw new CustomServiceException("given session is null");
        }
        StringBuilder builder = new StringBuilder();

        out.write("<p>Role</p><br>");
        out.write("<p>").
                append((String) properties.get("role")).
                append("</p><br>");
        out.write("<p>First name</p><br>");
        out.write("<p>").
                append((String) properties.get("firstName")).
                append("</p><br>");
        out.write("<p>Second name</p><br>");
        out.write("<p>").
                append((String) properties.get("lastName")).
                append("</p><br>");

        return builder.toString();
    }

    public String formSignOutButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<br><form action=\"/PROJECT_EPAM_JWD_war/sign_out\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"sign_out\">");
        out.write("<input type=\"submit\" value=\"Sign Out\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formFinishCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action = \"/PROJECT_EPAM_JWD_war/course_profile\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"finish_course\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value = \"Finish course\">");
        out.write("</form>");
        out.write("<br>");

        return builder.toString();
    }

    public String formStudentSignUpForCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"student_sign_up_for_course\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=").
                append(courseId).append(">");
        out.write("<input type=\"submit\" value=\"Sign up for course\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formTeacherManageCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"teacher_manage_course\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=").
                append(courseId).append(">");
        out.write("<input type=\"submit\" name=\"Manage course\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formCreateUserButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"sign_up\">");
        out.write("<input type=\"submit\" value=\"Sign up someone\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formRemoveTeacherFromCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"remove_teacher_from_course\">");
        out.write("<input type=\"submit\" value=\"Remove teacher from course\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formAppendTeacherOnCourseButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"append_teacher_on_course>");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"Append teacher on course\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formCourseHometasksButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"course_hometask\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"View hometask\">");
        out.write("</form>");
        out.write("<br>");

        return builder.toString();
    }

    public String formAnswerHometaskButton(int hometaskId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"answer_hometask\">");
        out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"").
                append(hometaskId).append("\">");
        out.write("<input type=\"submit\" value=\"Answer hometask\">");

        return builder.toString();
    }

    public String formCheckHometaskButton(int hometaskId, int studentId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"check_hometask\">");
        out.write("<input type=\"hidden\" name=\"hometask_id\" value=\"").
                append(hometaskId).append("\">");
        out.write("<input type=\"hidden\" name=\"student_id\" value=\"").
                append(studentId).append("\">");
        out.write("<input type=\"submit\" value=\"Check hometask\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formUserProfileButton() {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/user_profile\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"user_profile\">");
        out.write("<input type=\"submit\" value=\"Profile\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formUserActiveCourses(String buttonSign) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/active_courses\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"user_courses\">");
        out.write("<input type=\"hidden\" name=\"passed\" value=\"false\">");
        out.write("<input type=\"submit\" value=\"").append(buttonSign).
                append("\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formUserPassedCourses(String buttonSign) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/passed_courses\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"user_courses\">");
        out.write("<input type=\"hidden\" name=\"passed\" value=\"true\">");
        out.write("<input type=\"submit\" value=\"").append(buttonSign).
                append("\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formViewCourseInfoButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/course_info\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"course_profile\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"View\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formStudentLeaveCourse(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"leave_course\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"Leave course\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formStudentProgressButton(int courseId, int studentId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"student_progress\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"hidden\" name=\"student_id\" value=\"").
                append(studentId).append("\">");
        out.write("<input type=\"submit\" value=\"View progress\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formKickStudentOutFromCourse(int courseId, int studentId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"kick_student_page\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").append(courseId).append("\">");
        out.write("<input type=\"hidden\" name=\"student_id\" value=\"").append(studentId).append("\">");
        out.write("<input type=\"submit\" value=\"Kick student out\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formAddSubjectsForTeacher(int teacherId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"add_subject\">");
        out.write("<input type=\"hidden\" name=\"teacher_id\" value=\"").
                append(teacherId).append("\">");
        out.write("</form><br>");

        return builder.toString();
    }

   public String formStudentsListButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/university\">");
        out.write("<input type=\"hidden\" name=\"command\" value=\"students_on_course_list\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"Students list\">");
        out.write("</form><br>");

        return builder.toString();
    }

    public String formCreateHometaskButton(int courseId) {
        StringBuilder builder = new StringBuilder();

        out.write("<br>");
        out.write("<form action=\"/PROJECT_EPAM_JWD_war/hometask\">");
        out.write("<input type=\"hidden\" name=\"command\" " +
                "value=\"create_hometask\">");
        out.write("<input type=\"hidden\" name=\"course_id\" value=\"").
                append(courseId).append("\">");
        out.write("<input type=\"submit\" value=\"Create hometask\">");
        out.write("</form><br>");

        return builder.toString();
    }
}
