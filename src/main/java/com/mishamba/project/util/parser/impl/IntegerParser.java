package com.mishamba.project.util.parser.impl;

import com.mishamba.project.util.exception.UtilException;
import com.mishamba.project.util.parser.Parser;
import org.apache.log4j.Logger;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String input) throws UtilException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            throw new UtilException("can't parse input to integer");
        }
    }
}
