package com.mishamba.project.util.parser.impl;

import com.mishamba.project.exception.UtilException;
import com.mishamba.project.util.parser.Parser;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String input) throws UtilException {
        if (input == null) {
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            throw new UtilException("can't parse input to integer");
        }
    }
}
