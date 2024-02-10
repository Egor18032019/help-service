package org.example.servlet;

import org.example.context.ApplicationContext;
import org.example.utils.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FilterControllers implements Filter {
    private int count = 0;
    ApplicationContext applicationContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        // Get init parameter
        String testParam = filterConfig.getInitParameter("test-param");
        applicationContext = new ApplicationContext();

        System.out.println("!! Test Param: " + testParam);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        count++;
        System.out.println("!!!  @@@  doFilter");

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        System.out.println(req.getRequestURI());
        System.out.println(req.getMethod());
        String pathname = req.getServletPath();
        System.out.println("pathname " + pathname);
        var storageControllers = applicationContext.getStorageControllers();
        var storageInstances = applicationContext.getStorageInstances();
        var controller = storageControllers.get(pathname);
        System.out.println("controller = " + controller);
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        ServletInterface servlet = (ServletInterface) storageInstances.get(controller);
        String method = req.getMethod();

        if (method.equals("GET")) {
            servlet.doGet(req, response);
        }
        if (method.equals("POST")) {
            servlet.doPost(req, response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
