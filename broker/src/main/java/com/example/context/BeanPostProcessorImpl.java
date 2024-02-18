package com.example.context;



import com.example.service.MessageQueueImpl;
import lombok.RequiredArgsConstructor;
import org.example.annotation.Subscriber;


import org.example.subscriber.SubscriberInner;
import org.example.subscriber.SubscriberInterface;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class BeanPostProcessorImpl implements BeanPostProcessor {
    private final Map<String, Object> storageBeanForChange = new HashMap<>();
    private final ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Subscriber.class)) {
            storageBeanForChange.put(beanName, bean);
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        var obj = storageBeanForChange.get(beanName);
        if (obj != null) {
            System.out.println("Вызвано " + beanName);
            return new SubscriberInner((SubscriberInterface) bean, (MessageQueueImpl) applicationContext.getBean("messageQueueImpl"));
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
