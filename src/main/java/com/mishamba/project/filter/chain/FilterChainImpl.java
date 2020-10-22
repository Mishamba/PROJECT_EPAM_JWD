package com.mishamba.project.filter.chain;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class FilterChainImpl implements FilterChain {
    private Iterator<Filter> filterIterator;

    public FilterChainImpl(Filter ... filters){
        this.filterIterator = Arrays.asList(filters).listIterator();
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        if (filterIterator.hasNext()) {
            Filter filter = filterIterator.next();
            filter.doFilter(servletRequest, servletResponse, this);
        }
    }
}
