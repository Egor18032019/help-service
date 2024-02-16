package com.example.service;

import com.example.event.MessageQueueImpl;
import com.example.subscriber.Subscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class MessageServiceImpl implements MessageService{
    MessageQueueImpl messageQueue;
    Subscriber subscriber;



    @Override
    public boolean publish(String message) {
        return messageQueue.publish(message);
    }

    @Override
    public String poll() {
        return messageQueue.poll();
    }
}
