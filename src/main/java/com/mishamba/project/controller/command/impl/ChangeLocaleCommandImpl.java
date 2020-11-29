package com.mishamba.project.controller.command.impl;

import com.mishamba.project.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;


/**
 * This class changes locale
 *
 * @version 1.0
 * @author Mishamba
 */

public class ChangeLocaleCommandImpl implements Command {
    private final static Logger logger = Logger.getLogger(ChangeLocaleCommandImpl.class);
    private final String LOCALE = "locale";
    private final String EN_LANGUAGE = "en";
    private final String RU_LANGUAGE = "ru";
    private final String RU_COUNTRY = "RU";
    private final String US_COUNTRY = "US";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE);
        if (locale.getLanguage().equals(EN_LANGUAGE) &&
                locale.getCountry().equals(US_COUNTRY)) {
            locale = new Locale(RU_LANGUAGE, RU_COUNTRY);
        } else {
            locale = new Locale(EN_LANGUAGE, US_COUNTRY);
        }

        session.setAttribute(LOCALE, locale);

        // TODO: 11/14/20 save page
        try {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("can't send answer");
        }
    }
}
