package com.example.controllers;

import com.example.schemas.MessageRequest;
import com.example.schemas.MessageResponse;
import com.example.schemas.SupportResponse;
import com.example.service.KafkaService;
import com.example.store.GoodRepository;
import com.example.utils.EndPoint;
import com.example.utils.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = EndPoint.helpService + EndPoint.api)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class MessageController {

    GoodRepository goodRepository;
    KafkaService kafkaService;

    @PostMapping(value = EndPoint.support)
    public MessageResponse post(@RequestBody MessageRequest request) {
        kafkaService.send(request);
        return new MessageResponse(Status.SENT.toString());
    }


    @GetMapping(value = EndPoint.support)
    public SupportResponse get() {
        String message = goodRepository.getRandomPhrase();
        return new SupportResponse(message);
    }
}
