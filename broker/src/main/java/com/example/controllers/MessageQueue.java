package com.example.controllers;

public interface MessageQueue {
    public boolean publish(String message);

    public String poll();
}
