package org.example.logging;

public class LoggerServiceImpl implements LoggerService{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
