//package com.example.service;
//
//import com.example.controllers.MessageController;
//import com.example.store.GoodRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.kafka.core.KafkaTemplate;
//
//@Configuration
//@RequiredArgsConstructor
//public class ContextControllerConfig {
//    @Autowired
//    private Environment env;
//    ApplicationContext applicationContext;
//
//    @Bean
//    public KafkaService service() {
//        boolean kafkaNeed = Boolean.parseBoolean(env.getProperty("KAFKA_NEED"));
//
//        System.out.println("kafkaNeed " + kafkaNeed);
//        if (kafkaNeed) {
//            return new KafkaService(applicationContext.getBean(KafkaTemplate.class));
//        } else {
//            return new KafkaService();
//        }
//
//    }
//}
