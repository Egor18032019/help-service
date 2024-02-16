package com.example.service;

import com.example.event.MessageQueueImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    MessageQueueImpl messageQueue;

    @Override
    public boolean publish(String message) {
        return messageQueue.publish(message);
    }

    @Override
    public String poll() {
        return messageQueue.poll();
    }
}
