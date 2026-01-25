package com.haxon.remotelogmonitor.service;

import com.haxon.remotelogmonitor.dto.user.CreateUserRequestDto;
import com.haxon.remotelogmonitor.entities.User;
import com.haxon.remotelogmonitor.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User createUser(CreateUserRequestDto dto) {
        if (userRepository.findUserByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid details");
        }
        return userRepository.save(User.builder().fullName(dto.getFullName()).email(dto.getEmail())
                .passwordHash(encoder.encode(dto.getPassword())).isActive(true).build());
    }
}
