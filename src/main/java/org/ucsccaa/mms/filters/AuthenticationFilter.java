package org.ucsccaa.mms.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.ucsccaa.mms.models.ServiceResponse;
import org.ucsccaa.mms.models.Status;
import org.ucsccaa.mms.services.AuthenticationService;
import org.ucsccaa.mms.services.MemberService;
import org.ucsccaa.mms.services.impl.AuthenticationServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/member/*", "/staff/*"})
@Order(value = 1)
public class AuthenticationFilter implements Filter {
    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String authHead = httpServletRequest.getHeader("authorization");
        System.out.println(authHead.substring(0,6));
        System.out.println(authHead);
        try {
            if (authHead == null || !"Bearer".equals(authHead.substring(0, 6)) || authenticationService.validateToken(authHead.substring(6))) {
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