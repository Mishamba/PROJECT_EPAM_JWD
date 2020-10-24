package com.mishamba.project.util.parser.impl;

import com.mishamba.project.exception.UtilException;
import com.mishamba.project.util.parser.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser implements Parser<Date> {
    public Date parse(String date) throws UtilException {
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
