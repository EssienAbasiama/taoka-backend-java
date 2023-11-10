package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Model.MessageModel;
import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message){
        System.out.println("handling send message" + message+ "to "+ to);
         User user = userRepository.findByEmail(to).orElseThrow();
         simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);


    }
}