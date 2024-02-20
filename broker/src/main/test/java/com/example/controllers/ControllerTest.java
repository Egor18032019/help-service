package com.example.controllers;

import com.example.BrokerApp;
import com.example.service.MessageQueueImpl;
import com.example.store.GoodRepository;
import com.example.subscriber.SubscriberWithAnnotation;
import com.example.utils.EndPoint;
import org.example.subscriber.SubscriberInner;
import org.example.subscriber.SubscriberInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BrokerApp.class)
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private GoodRepository repository;
    @SpyBean
    private SubscriberWithAnnotation subscriberWithAnnotation;

    @SpyBean
    private SubscriberInterface subscriberInterface;
    @Mock
    private SubscriberInner subscriberInner;
    @SpyBean
    private MessageQueueImpl messageQueue;
    @AfterEach
    public void resetDb() {
        // очистка
    }

    @Test()
    public void doGetFirsRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                                (EndPoint.helpService+EndPoint.api+EndPoint.support)
                )
                .andExpect(status().isOk());

    }
}
