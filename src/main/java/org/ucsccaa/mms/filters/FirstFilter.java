package org.ucsccaa.mms.filters;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "general filter", urlPatterns = "/*")
@Order(value = 1)
public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--first filter");
        filterChain.doFilter(req, resp);
    }
}
