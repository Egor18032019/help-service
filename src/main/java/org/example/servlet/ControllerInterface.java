package org.example.servlet;

import org.example.annotation.Logged;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerInterface {
    @Logged
    void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException;

    @Logged
    void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException ;
}
