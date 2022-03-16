package ca.mcgill.ecse321.repairshopmanagementsystem;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT,DELETE ");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        response.setHeader("new_authorization", "new_authorization");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

