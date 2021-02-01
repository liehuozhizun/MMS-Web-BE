package org.ucsccaa.mms.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/member/*", "/staff/*"})
@Order(value = 1)
public class AuthenticationFilter implements Filter {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = httpServletRequest.getHeader("authorization").substring(6);
        try {
            Boolean result = authenticationService.validateToken(token);

            if (!result) {
                httpServletResponse.sendError(500, "INVALID TOKEN");
                return;
            }
        } catch (Exception e) {
            httpServletResponse.sendError(500, "INVALID TOKEN");
            return;
        }
        chain.doFilter(httpServletRequest, httpServletResponse);

    }
}