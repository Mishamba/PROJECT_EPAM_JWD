package com.mishamba.project.controller.command.factory;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.impl.GetCoursesCatalogCommand;
import com.mishamba.project.controller.command.impl.GetMainPageCommand;

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
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.HOLDER;
    }

    public Command getCommand(String commandType) {
        return repository.get(commandType);
    }
}
