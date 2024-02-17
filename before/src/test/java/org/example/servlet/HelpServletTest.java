package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.context.ApplicationContext;
import org.example.schemas.HelpRequest;
import org.example.store.GoodRepository;
import org.example.utils.Const;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpServletTest {
    private ControllerInterface helpServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;
    private GoodRepository goodRepository;
    private   ApplicationContext applicationContext ;

    @BeforeEach
    public void setUp() throws IOException {
          applicationContext = new ApplicationContext();
        helpServlet= applicationContext.getInstance(HelpControllerImpl.class);

        goodRepository = applicationContext.getInstance(GoodRepository.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();

        PrintWriter writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test()
    public void doGetFirsRequest() throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        helpServlet.doGet(request, response);
        System.out.println("! size " + goodRepository.getSizeStorage());
        assertTrue(responseWriter.toString().contains(Const.default_phrase));
    }


    @Test()
    public void doPostRequest() throws IOException {
        request.setCharacterEncoding("application/json; charset=UTF-8");
        String phrase = "фраза из теста doPostRequest ";
        HelpRequest helpRequest = new HelpRequest(phrase);
        String answer = new ObjectMapper().writeValueAsString(helpRequest);
        when(request.getInputStream()).thenReturn(
                new DelegatingServletInputStream(
                        new ByteArrayInputStream(answer.getBytes(StandardCharsets.UTF_8))));
        when(request.getReader()).thenReturn(
                new BufferedReader(new StringReader(answer)));
        when(request.getContentType()).thenReturn("application/json; charset=UTF-8");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        int sizeStorageBeforeRequest = goodRepository.getSizeStorage();

        helpServlet.doPost(request, response);

        int sizeStorageAfterRequest = goodRepository.getSizeStorage();
        boolean isAdded = sizeStorageAfterRequest > sizeStorageBeforeRequest;
        boolean isHave = responseWriter.toString().contains("ADDED");
        assertTrue(isHave && isAdded);
    }
    @Test()
    public void repository_size_should_increase() {
        applicationContext = new ApplicationContext();
        goodRepository = applicationContext.getInstance(GoodRepository.class);
        goodRepository.add("фраза из теста  repository_size_should_increase");
        assertEquals(1, goodRepository.getSizeStorage());
    }


}
