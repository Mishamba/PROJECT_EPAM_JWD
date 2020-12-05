package com.mishamba.project.filter.parameters;

import com.mishamba.project.util.parser.Parser;
import com.mishamba.project.util.parser.impl.BooleanParser;
import com.mishamba.project.util.parser.impl.DateParser;
import com.mishamba.project.util.parser.impl.IntegerParser;

import java.util.HashMap;
import java.util.Map;

public class ParametersParserHolder {
    private static Map<String, Parser> parserMap;
    private static String USER_ID = "user_id";
    private static String COURSE_ID = "course_id";
    private final String BIRTHDAY = "birthday";
    private final String DEADLINE = "deadline";
    private final String HOMETASK_ID = "hometask_id";
    private final String STUDENT_ID = "student_id";
    private final String MARK = "mark";
    private final String FINISHED = "finished";

    private ParametersParserHolder() {
        parserMap = new HashMap<>();
        parserMap.put(USER_ID, new IntegerParser());
        parserMap.put(BIRTHDAY, new DateParser());
        parserMap.put(DEADLINE, new DateParser());
        parserMap.put(HOMETASK_ID, new IntegerParser());
        parserMap.put(STUDENT_ID, new IntegerParser());
        parserMap.put(MARK, new IntegerParser());
        parserMap.put(COURSE_ID, new IntegerParser());
        parserMap.put(FINISHED, new BooleanParser());
    }

    private static class ParametersParserHolderHolder {
        private static final ParametersParserHolder HOLDER =
                new ParametersParserHolder();
    }

    public static ParametersParserHolder getInstance() {
        return ParametersParserHolderHolder.HOLDER;
    }

    public Parser<?> getParser(String parameterName) {
        return parserMap.get(parameterName);
    }
}
