package com.example.controllers;

import com.example.utils.EndPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        // очистка
    }

    @Test()
    public void doGetFirsRequest() throws Exception {
        System.out.println("doGetFirsRequest");
        mockMvc.perform(MockMvcRequestBuilders.get
                                (EndPoint.helpService+EndPoint.api+EndPoint.support)
                )
                .andExpect(status().isOk());



    }
}
