package com.rusiri.websocket.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("user")
public class User {
    
    @Id
    private String nickName;

    private String fullName;
    
    private Status status;

    public User(String nickName, String fullName, Status status) {
        this.nickName = nickName;
        this.fullName = fullName;
        this.status = status;
    }

    
}
