package com.mishamba.project.filter;

import com.mishamba.project.filter.chain.FilterChainImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterManager {
    public static void process(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException, ServletException {
        FilterChain filterChain = new FilterChainImpl();
        filterChain.doFilter(request, response);
    }
}
