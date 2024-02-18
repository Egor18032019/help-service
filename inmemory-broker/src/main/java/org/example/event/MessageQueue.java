package org.example.event;

public interface MessageQueue {
    boolean publish(String message);

    String poll();
}
