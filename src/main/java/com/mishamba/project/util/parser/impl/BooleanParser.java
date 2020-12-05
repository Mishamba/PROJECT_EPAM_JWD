package com.mishamba.project.util.parser.impl;

import com.mishamba.project.exception.UtilException;
import com.mishamba.project.util.parser.Parser;

public class BooleanParser implements Parser<Boolean> {
    @Override
    public Boolean parse(String input) throws UtilException {
        return Boolean.parseBoolean(input);
    }
}
