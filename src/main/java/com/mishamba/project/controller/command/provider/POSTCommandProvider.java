package com.mishamba.project.controller.command.provider;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.impl.SignInProcessCommand;
import com.mishamba.project.controller.command.impl.SignUpProcessCommand;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class POSTCommandProvider {
    private final static Logger logger = Logger.getLogger(POSTCommandProvider.class);
    private final Map<String, Command> repository = new HashMap<>();

    private static class POSTCommandProviderHolder {
        private static final POSTCommandProvider HOLDER = new POSTCommandProvider();
    }

    private POSTCommandProvider() {
        repository.put("sign_in_check", new SignInProcessCommand());
        repository.put("sign_up_check", new SignUpProcessCommand());
    }

    public static POSTCommandProvider getInstance() {
        return POSTCommandProviderHolder.HOLDER;
    }

    public Command getCommand(String commandType) {
        logger.info("getting Command by \"" + commandType + "\"");
        return repository.get(commandType);
    }
}
