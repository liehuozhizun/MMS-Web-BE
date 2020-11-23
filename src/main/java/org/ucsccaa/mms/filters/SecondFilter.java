package org.ucsccaa.mms.filters;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//Interceptor
@WebFilter(filterName = "member sec filter", urlPatterns = "/members/*")
@Order(value = 2)
public class SecondFilter implements Filter {

    private final String fixedToken = "Bearer JWTDOS82d2&*(TGI#@";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--second filter");

        String token = ((HttpServletRequest)servletRequest).getHeader("Authorization");
        if (fixedToken.equals(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.err.println("ERROR: WRONG TOKEN!!!!! : " + token);
        }
    }
}
