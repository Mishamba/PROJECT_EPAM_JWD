package com.mishamba.project.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "BaseFilter")
public class BaseFilter implements Filter {
    private static final Logger logger = Logger.getLogger(BaseFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain)
            throws ServletException, IOException {
        FilterManager.process(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
