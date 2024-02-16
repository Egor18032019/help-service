package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotation.Controller;
import org.example.schemas.HelpRequest;
import org.example.schemas.HelpResponse;
import org.example.store.GoodRepository;
import org.example.utils.Const;
import org.example.utils.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller(path = Const.support)
public class HelpControllerImpl implements ControllerInterface {
    private final GoodRepository goodRepository;

    public HelpControllerImpl(GoodRepository repository) {
        goodRepository = repository;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phrase = goodRepository.getRandomPhrase();

        HelpRequest helpRequest = new HelpRequest(phrase);
        String answer = new ObjectMapper().writeValueAsString(helpRequest);

        PrintWriter writer = response.getWriter();
        writer.println(answer);
        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HelpRequest helpRequest = new ObjectMapper().readValue(req.getReader(),HelpRequest.class);
        goodRepository.add(helpRequest.getPhrase());
        HelpResponse helpResponse = new HelpResponse(Status.ADDED.toString());
        String answer = new ObjectMapper().writeValueAsString(helpResponse);

        PrintWriter writer = resp.getWriter();
        writer.println(answer);
        writer.close();
    }

}