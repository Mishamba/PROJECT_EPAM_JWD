package com.mishamba.project.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private final String EMAIL_REG_EXP = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public boolean validate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REG_EXP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
