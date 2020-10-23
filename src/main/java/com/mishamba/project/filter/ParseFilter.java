package com.mishamba.project.filter;

import com.mishamba.project.controller.command.Command;
import com.mishamba.project.filter.parameters.CommandParametersHolder;
import com.mishamba.project.filter.parameters.ParametersParserHolder;
import com.mishamba.project.util.exception.UtilException;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "ParseFilter")
public class ParseFilter implements Filter {
    private static final Logger logger = Logger.getLogger(ParseFilter.class);
    private final String COMMAND = "command";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain)
            throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND);
        List<String> parameters = CommandParametersHolder.getInstance().
                getParametersList(commandName);

        for (String parameter : parameters) {
            try {
                req.setAttribute(parameter, ParametersParserHolder.getInstance().
                        getParser(parameter).parse(req.getParameter(parameter)));
            } catch (UtilException e) {
                logger.error("can't parse parameters. sending error page");
                HttpServletRequest request = (HttpServletRequest) req;
                request.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
