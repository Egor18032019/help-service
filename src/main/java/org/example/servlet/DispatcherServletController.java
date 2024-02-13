package org.example.servlet;

import org.example.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServletController implements Filter {

    private ApplicationContext applicationContext;

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

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        ControllerInterface servlet = getControllerForPath(req);
        String method = req.getMethod();
// при добавлении других методов расширяем ServletInterface
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

    private ControllerInterface getControllerForPath(HttpServletRequest req) {
        var storageControllers = applicationContext.getStorageControllers();
        var storageInstances = applicationContext.getStorageInstances();
        String pathname = req.getServletPath();
        var controller = storageControllers.get(pathname);
        return (ControllerInterface) storageInstances.get(controller);
    }
//todo сделать рефакторинг(solid и clean)

}
