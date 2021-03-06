package com.mishamba.project.controller.command.provider;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.impl.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GETCommandProvider {
    private final Logger logger = Logger.getLogger(GETCommandProvider.class);
    private final Map<String, Command> repository = new HashMap<>();

    private static class GETCommandProviderHolder {
        private static final GETCommandProvider HOLDER = new GETCommandProvider();
    }

    private GETCommandProvider() {
        repository.put("main_page", new GetMainPageCommand());
        repository.put("courses_catalog", new GetCoursesCatalogCommand());
        repository.put("sign_up", new GetSignUpPageCommand());
        repository.put("sign_in", new GetSignInPageCommand());
        repository.put("sign_in_check", new SignInProcessCommand());
        repository.put("sign_up_check", new SignUpProcessCommand());
        repository.put("create_hometask", new GetCreateHometaskPageCommand());
        repository.put("sign_out", new SignOutProcessCommand());
        repository.put("course_profile", new GetCourseProfileCommand());
        repository.put("student_sign_up_for_course", new StudentSignUpOnCourseCommand());
        repository.put("user_profile", new GetUserProfilePageCommand());
        repository.put("user_courses", new GetUserCoursesCommand());
        repository.put("students_on_course_list", new GetStudentsOnCourseListCommand());
        repository.put("course_hometask", new GetCourseHometaskPageCommand());
        repository.put("check_create_hometask", new CreateHometaskProcessCommand());
        repository.put("answer_hometask", new GetAnswerHometaskPageCommand());
        repository.put("enter_hometask_answer", new AnswerHometaskProcessCommand());
        repository.put("student_progress", new GetStudentProgressCommand());
        repository.put("check_hometask", new GetCheckHometaskPageCommand());
        repository.put("set_hometask_mark", new SetHometaskMarkProcessCommand());
        repository.put("kick_student_page", new GetKickStudentOutPageCommand());
        repository.put("change_locale", new ChangeLocaleCommandImpl());
        repository.put("kick_student_process", new KickStudentProcessCommand());
        repository.put("append_teacher_on_course", new AppendTeacherOnCourseProcessCommand());
    }

    public static GETCommandProvider getInstance() {
        return GETCommandProviderHolder.HOLDER;
    }

    public Command getCommand(String commandType) {
        logger.info("getting Command by \"" + commandType + "\"");
        return repository.get(commandType);
    }
}
