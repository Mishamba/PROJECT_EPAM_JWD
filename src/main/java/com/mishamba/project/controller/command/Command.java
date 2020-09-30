package com.mishamba.project.controller.command;

import com.mishamba.project.controller.exception.ControllerException;

import java.util.Properties;

public interface Command {
    String execute(Properties parameter) throws ControllerException;
}
