package org.ucsccaa.mms.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/members/*", "/staff/*"})
@Order(value = 1)
public class AuthenticationFilter implements Filter {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Value("${fixtoken}")
    private String fixtoken;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String authHead = httpServletRequest.getHeader("authorization");
        System.out.println(fixtoken);
        System.out.println(authHead);
        if (fixtoken.equals(authHead)){
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        try {
            if (authHead == null || !"Bearer".equals(authHead.substring(0, 6)) || authenticationService.validateJwtToken(authHead.substring(6))) {
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