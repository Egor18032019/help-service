package org.example.servlet;

import org.example.context.ApplicationContext;
import org.example.store.GoodRepository;
import org.example.utils.Const;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpServletTest {
    private HelpServlet helpServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;
    private ApplicationContext applicationContext ;


    @BeforeEach
    public void setUp() throws IOException, InvocationTargetException, IllegalAccessException {
        applicationContext = new ApplicationContext();
        helpServlet =  applicationContext.getInstance(HelpServlet.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();

        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test()
    public void doGetFirsRequest() throws IOException, InvocationTargetException, IllegalAccessException {


        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        helpServlet.doGet(request, response);

        assertTrue(responseWriter.toString().contains(Const.default_phrase));
    }


    @Test()
    public void doPostRequest() throws IOException {
        request.setCharacterEncoding("text/plain");

        String text = "text";

        when(request.getInputStream()).thenReturn(
                new DelegatingServletInputStream(
                        new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(
                new BufferedReader(new StringReader(text)));
        when(request.getContentType()).thenReturn("text/html");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        GoodRepository goodRepository = applicationContext.getInstance(GoodRepository.class);
        int sizeStorageBeforeRequest = goodRepository.getSizeStorage();

        helpServlet.doPost(request, response);

        int sizeStorageAfterRequest = goodRepository.getSizeStorage();
        boolean isAdded = sizeStorageAfterRequest > sizeStorageBeforeRequest;
        boolean isHave = responseWriter.toString().contains("ADDED");
        assertTrue(isHave && isAdded);

    }

    @Test()
    public void manager_should_return_phrase() throws InvocationTargetException, IllegalAccessException {

        GoodRepository goodRepository = applicationContext.getInstance(GoodRepository.class);
        String supportPhrase = goodRepository.getRandomPhrase();
        assertEquals(supportPhrase, Const.default_phrase);
    }
}
