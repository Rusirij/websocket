package com.rusiri.websocket.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        repo.save(user);
    }
    
    public void disconnect(User user){
        var storedUser = repo.findById(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repo.save(storedUser);
        }

    }

    public List<User> findConnectedUsers(){        
        return repo.findAllByStatus(Status.ONLINE);
    }
}
