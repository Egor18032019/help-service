package com.example.service;


import lombok.AllArgsConstructor;

import org.example.event.MessageQueue;
import org.example.event.MessageService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    MessageQueue messageQueue;

    @Override
    public boolean publish(String message) {
        return messageQueue.publish(message);
    }

    @Override
    public String poll() {
        return messageQueue.poll();
    }
}
