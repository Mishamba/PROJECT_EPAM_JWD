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
        repository.put("sing_up", new GetSingUpPageCommand());
        repository.put("sing_in", new GetSignInPageCommand());
        repository.put("sing_in_check", new SingInProcessCommand());
        repository.put("sing_up_check", new SingUpProcessCommand());
        repository.put("create_hometask", new GetHometaskCreatePageCommand());
        repository.put("sing_out", new SignOutProcessCommand());
        repository.put("course_profile", new GetCourseProfileCommand());
        repository.put("student_sing_up_for_course", new StudentSingUpOnCourseCommand());
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.HOLDER;
    }

    public Command getCommand(String commandType) {
        return repository.get(commandType);
    }
}
