package com.example.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanPostProcessorToggle implements BeanPostProcessor {
    @Autowired
    private Environment env;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        boolean kafkaNeed = Boolean.parseBoolean(env.getProperty("KAFKA_NEED"));
//        if (!kafkaNeed) {
//
//            if (beanName.equalsIgnoreCase("producerConfiguration")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("org.springframework.kafka.config.internalKafkaListenerEndpointRegistry")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//
//            if (beanName.equalsIgnoreCase("producerConfigurations")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("kafkaTemplate")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("producerFactory")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("consumerConfiguration")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("kafkaListenerContainerFactory")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("consumerFactory")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("consumerConfigurations")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//            if (beanName.equalsIgnoreCase("consumer")) {
//                System.out.println("beanName " + beanName);
//                return null;
//            }
//        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
