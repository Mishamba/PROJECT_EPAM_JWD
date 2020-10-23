package com.mishamba.project.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class FilterManager {
    public static void process(ServletRequest request,
                               ServletResponse response)
            throws IOException, ServletException {
        FilterChain filterChain = new FilterChainImpl(new RightsFilter(),
                new ParseFilter());
        filterChain.doFilter(request, response);
    }
}
