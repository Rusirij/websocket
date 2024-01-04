package com.rusiri.websocket.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserService service;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic") //new queue
    public User addUser(@Payload User user) {
        service.saveUser(user);
        return user;
    }


    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic") //new queue
    public User disconnectUser(@Payload User user) {
        service.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(service.findConnectedUsers());
    }
}
