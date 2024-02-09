package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.context.ApplicationContext;
import org.example.schemas.HelpRequest;
import org.example.store.GoodRepository;
import org.example.utils.Util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class HelpServletImpl extends HttpServlet implements HelpServlet {
    private final GoodRepository goodRepository;


    public HelpServletImpl(  ) {
//        goodRepository=new GoodRepositoryImpl();
        ApplicationContext applicationContext = new ApplicationContext();
        goodRepository = applicationContext.getInstance(GoodRepository.class);
    }
    public HelpServletImpl(GoodRepository repository  ) {
        goodRepository=repository;
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phrase = goodRepository.getRandomPhrase();
        System.out.println("size storage " + goodRepository.getSizeStorage());
//        String answer = "<html lang=\"ru\">\n" +
//                "<head>\n" +
//                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
//                "    <title>HELP-SERVICE</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<h1>" + phrase + "</h1>\n" +
//                "</body>\n" +
//                "</html>";

        HelpRequest helpRequest = new HelpRequest(phrase);
        String answer =new ObjectMapper().writeValueAsString(helpRequest);
                PrintWriter writer = response.getWriter();
        writer.println(answer);

        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String text = Util.convertToString(bufferedReader);
        goodRepository.add(text);
        resp.getWriter().println("ADDED");
    }
}