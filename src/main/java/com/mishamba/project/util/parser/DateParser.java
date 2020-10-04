package com.mishamba.project.util.parser;

import com.mishamba.project.util.exception.UtilException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    public Date parseDate(String date) throws UtilException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new UtilException("can't parse this date", e);
        }
    }
}
