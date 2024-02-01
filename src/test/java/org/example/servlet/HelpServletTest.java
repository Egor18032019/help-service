package org.example.servlet;

import org.example.utils.Const;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpServletTest {
    private HelpServlet helpServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    @BeforeEach
    public void setUp() throws IOException {
        helpServlet = new HelpServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test()
    public void doGetFirsRequest() throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        helpServlet.doGet(request, response);

        assertTrue(responseWriter.toString().contains(Const.def));
    }
    //todo тест на Post не сделан. не могу понять как
    @Test()
    public void doPostRequest() throws IOException {
        request.setCharacterEncoding("text/plain");

// положить значения в request
        helpServlet.doPost(request, response);
        System.out.println(response.getWriter().toString());
        // проверить что в хранилище +1 + статус ответа + ответ

        assertTrue(response.getWriter().toString().contains("ADDED"));
    }
}
