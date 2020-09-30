package com.mishamba.project.controller;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.controller.command.factory.CommandProvider;
import com.mishamba.project.controller.exception.ControllerException;

import java.util.Properties;

public class Invoker {
    private Invoker() {}

    private static class InvokerHolder {
        private static final Invoker HOLDER = new Invoker();
    }

    public static Invoker getInstance() {
        return InvokerHolder.HOLDER;
    }

    public String executeCommand(Properties parameter) {
        Command executor = CommandProvider.getInstance().getCommand(parameter);
        try {
            return executor.execute(parameter);
        } catch (ControllerException e) {

        }

        return null;
    }
}
