package com.mishamba.project.util.validator;

import java.util.Date;

public class DateValidator {
    public boolean checkForFuture(Date date) {
        if (date == null) {
            return true;
        }

        return (date.after(new Date()));
    }
}
