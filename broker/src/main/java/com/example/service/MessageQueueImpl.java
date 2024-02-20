package com.example.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.event.MessageQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@NoArgsConstructor
public class MessageQueueImpl implements MessageQueue {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    @Override
    public boolean publish(String message) {
        boolean offer = queue.offer(message);
        return offer;
    }

    @Override
    public String poll() {
        return queue.poll();
    }

}