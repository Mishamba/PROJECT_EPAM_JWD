package com.mishamba.project.util.parser;

import com.mishamba.project.util.exception.UtilException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    public Date parseDate(String date) throws UtilException {
        if (date == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new UtilException("can't parse this date", e);
        }
    }

    public String parseDateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd",
                Locale.ENGLISH);
        return format.format(date);
    }
}
