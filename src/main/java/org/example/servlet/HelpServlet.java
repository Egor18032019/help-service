package org.example.servlet;

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

public class HelpServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String phrase = GoodRepository.getRandomPhrase();
        System.out.println("size storage " + GoodRepository.getSizeStorage());
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
//        writer.append(answer);
//       writer.print(answer);
//      writer.write(answer);
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String text = Util.convertToString(bufferedReader);
        GoodRepository.add(text);
        resp.getWriter().println("ADDED");
    }
}