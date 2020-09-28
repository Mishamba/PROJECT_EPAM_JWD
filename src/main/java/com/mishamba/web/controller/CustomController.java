package com.mishamba.web.controller;

import com.mishamba.web.controller.exception.ControllerException;

public interface CustomController {
    String execute() throws ControllerException;
}
