package com.example.subscriber;

import com.example.network.HttpURLConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class Subscriber implements Runnable {
    HttpURLConnectionService httpURLConnectionService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        while (true) {
            String message = "";
            try {
                message = httpURLConnectionService.sendHttpGETRequest();
            } catch (IOException e) {
                System.out.println("нет никого");
            }
            if (message != null) {
                System.out.println("Вытащил message = " + message);

            } else {
                try {
                    Thread.sleep(111111);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Subscriber interrupted");
                    break;
                }
            }

        }
    }
}
