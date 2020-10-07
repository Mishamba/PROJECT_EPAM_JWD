package com.mishamba.project.util.validator;

import java.util.Date;

public class DateValidator {
    public boolean inFuture(Date date) {
        if (date == null) {
            return true;
        }

        return (date.compareTo(new Date()) < 0);
    }
}
