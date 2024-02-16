package com.example.controllers;

import com.example.event.MessageQueueImpl;
import com.example.schemas.MessageRequest;
import com.example.schemas.MessageResponse;
import com.example.schemas.SupportResponse;
import com.example.service.MessageServiceImpl;
import com.example.utils.EndPoint;
import com.example.utils.Status;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = EndPoint.helpService +EndPoint.api)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl service) {
        this.messageService = service;
    }

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
        String message = messageService.poll();

        return new SupportResponse(message);

    }
}
