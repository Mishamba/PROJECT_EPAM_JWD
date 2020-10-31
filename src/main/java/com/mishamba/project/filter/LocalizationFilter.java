package com.mishamba.project.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocalizationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LocalizationFilter.class);
    private final String LOCALE = "locale";
    private final String FILE_NAME = "sign.text";
    private final String LANGUAGE = "EN";
    private final String COUNTRY = "US";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE);
        if (locale == null) {
            locale = new Locale(LANGUAGE, COUNTRY);

            session.setAttribute(LOCALE, locale);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
