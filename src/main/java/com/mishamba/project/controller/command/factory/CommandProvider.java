package com.mishamba.project.controller.command.factory;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private final Map<String, Command> repository = new HashMap<>();

    private static class CommandProviderHolder {
        private static final CommandProvider HOLDER = new CommandProvider();
    }

    private CommandProvider() {
        repository.put("main_page", new GetMainPageCommand());
        repository.put("courses_catalog", new GetCoursesCatalogCommand());
        repository.put("sign_up", new GetSignUpPageCommand());
        repository.put("sign_in", new GetSignInPageCommand());
        repository.put("sign_in_check", new SignInProcessCommand());
        repository.put("sign_up_check", new SignUpProcessCommand());
        repository.put("create_hometask", new GetHometaskCreatePageCommand());
        repository.put("sign_out", new SignOutProcessCommand());
        repository.put("course_profile", new GetCourseProfileCommand());
        repository.put("student_sign_up_for_course", new StudentSignUpOnCourseCommand());
        repository.put("user_profile", new GetUserProfilePageCommand());
        repository.put("user_courses", new GetUserCoursesCommand());
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.HOLDER;
    }

    public Command getCommand(String commandType) {
        return repository.get(commandType);
    }
}
