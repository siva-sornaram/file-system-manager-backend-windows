package com.siva;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;


public class RestCorsFilter implements Filter {

    @Override
    public void destroy() {
    // enter code here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        resp.addHeader("Access-Control-Allow-Methods", "*");
        
        chain.doFilter(request, resp);
        
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

}