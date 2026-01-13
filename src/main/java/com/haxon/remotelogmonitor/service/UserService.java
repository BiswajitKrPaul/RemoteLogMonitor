package com.haxon.remotelogmonitor.service;

import com.haxon.remotelogmonitor.entities.User;
import com.haxon.remotelogmonitor.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String email) {
        return userRepository.findUserByEmail(email)
                .orElse(null);
    }
}
