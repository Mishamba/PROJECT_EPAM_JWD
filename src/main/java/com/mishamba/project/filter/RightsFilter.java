package com.mishamba.project.filter;

import com.mishamba.project.filter.right.RightsHolder;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RightsFilter")
public class RightsFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RightsFilter.class);
    private final String ROLE = "role";
    private final String COMMAND_NAME = "command";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String commandName = request.getParameter(COMMAND_NAME);
        String role = (String) session.getAttribute(ROLE);

        if (!RightsHolder.getInstance().rightsCorrect(commandName, role)) {
            String ipAddress = request.getRemoteAddr();

            logger.warn("user with address : " + ipAddress +
                    " : tries to get responce for command : " +
                    commandName + " : with role : " + role + " :");
            response.sendError(403);
            request.getRequestDispatcher("error.html").
                    forward(request, response);
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
