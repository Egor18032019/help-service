package com.example.controllers;

import com.example.schemas.MessageRequest;
import com.example.schemas.MessageResponse;
import com.example.schemas.SupportResponse;
import com.example.service.MessageServiceImpl;
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
    MessageServiceImpl messageService;
    GoodRepository goodRepository;
 
    @PostMapping(value = EndPoint.support)
    public MessageResponse post(@RequestBody MessageRequest request) {
        boolean isAdd = messageService.publish(request.getPhrase());
        if (isAdd) {
            return new MessageResponse(Status.ADDED.toString());
        } else {
            return new MessageResponse(Status.REJECTED.toString());
        }
    }

    @GetMapping(value = EndPoint.support)
    public SupportResponse get() {
        String message = goodRepository.getRandomPhrase();
        return new SupportResponse(message);
    }
}
