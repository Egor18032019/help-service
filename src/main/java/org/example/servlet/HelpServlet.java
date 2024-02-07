package org.example.servlet;

import org.example.store.GoodRepository;
import org.example.store.GoodRepositoryImpl;
import org.example.utils.Util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class HelpServlet extends HttpServlet {
    private final GoodRepository goodRepository;

    //todo сделать интерфейс
    public HelpServlet(GoodRepository goodRepository) {
        this.goodRepository = new GoodRepositoryImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phrase = goodRepository.getRandomPhrase();
        System.out.println("size storage " + goodRepository.getSizeStorage());
        String answer = "<html lang=\"ru\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "    <title>HELP-SERVICE</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>" + phrase + "</h1>\n" +
                "</body>\n" +
                "</html>";

        PrintWriter writer = response.getWriter();
        writer.println(answer);

        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String text = Util.convertToString(bufferedReader);
        goodRepository.add(text);
        resp.getWriter().println("ADDED");
    }
}