package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotation.Controller;
import org.example.context.ApplicationContext;
import org.example.schemas.HelpRequest;
import org.example.schemas.HelpResponse;
import org.example.store.GoodRepository;
import org.example.utils.Const;
import org.example.utils.Path;
import org.example.utils.Status;
import org.example.utils.Util;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Controller(path = Const.support)
public class HelpServletImpl  implements ServletInterface {
    private final GoodRepository goodRepository;


    public HelpServletImpl() {
        ApplicationContext applicationContext = new ApplicationContext();
        goodRepository = applicationContext.getInstance(GoodRepository.class);
        System.out.println("!!!!  HelpServletImpl  запустили");
    }

    public HelpServletImpl(GoodRepository repository) {
        goodRepository = repository;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phrase = goodRepository.getRandomPhrase();
        System.out.println("size storage " + goodRepository.getSizeStorage());


        HelpRequest helpRequest = new HelpRequest(phrase);
        String answer = new ObjectMapper().writeValueAsString(helpRequest);

        PrintWriter writer = response.getWriter();
        writer.println(answer);
        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HelpRequest helpRequest = objectMapper.readValue(req.getReader().lines().reduce("", String::concat), HelpRequest.class);
        goodRepository.add(helpRequest.getPhrase());
        HelpResponse helpResponse = new HelpResponse(Status.ADDED.toString());
        String answer = new ObjectMapper().writeValueAsString(helpResponse);

        PrintWriter writer = resp.getWriter();
        writer.println(answer);
        writer.close();
    }

    @Override
    public String getPath() {
        return Path.support.getUrl();
    }


}