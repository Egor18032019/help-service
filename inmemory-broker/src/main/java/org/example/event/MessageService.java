package org.example.event;

public interface MessageService {
    public boolean publish(String message);
    public String poll();
}
