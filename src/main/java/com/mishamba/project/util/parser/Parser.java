package com.mishamba.project.util.parser;

import com.mishamba.project.util.exception.UtilException;

public interface Parser<T> {
    T parse(String input) throws UtilException;
}
