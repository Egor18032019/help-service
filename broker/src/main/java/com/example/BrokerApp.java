package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
public class BrokerApp {
    public static void main(String[] args) {
        SpringApplication.run(BrokerApp.class, args);
    }
}
