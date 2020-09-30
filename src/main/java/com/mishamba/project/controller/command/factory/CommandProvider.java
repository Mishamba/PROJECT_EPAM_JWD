package com.mishamba.project.controller.command.factory;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.impl.GetCoursesCatalogCommand;
import com.mishamba.project.controller.command.impl.GetMainCoursesCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommandProvider {
    private final Map<String, Command> repository = new HashMap<>();

    private static class CommandProviderHolder {
        private static final CommandProvider HOLDER = new CommandProvider();
    }

    private CommandProvider() {
        repository.put("mainCourses", new GetMainCoursesCommand());
        repository.put("coursesCatalog", new GetCoursesCatalogCommand());
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.HOLDER;
    }

    public Command getCommand(Properties parameter) {
        return repository.get(parameter.get("commandType"));
    }
}
