package com.example.service;

public interface MessageService {
    public boolean publish(String message);
    public String poll();
}
