package com.mishamba.project.util.validator;

public class MarkValidator {
    private final int MIN_MARK = 0;
    private final int MAX_MARK = 10;

    public boolean isCorrect(int mark) {
        return (mark > MIN_MARK && mark <= MAX_MARK);
    }
}
