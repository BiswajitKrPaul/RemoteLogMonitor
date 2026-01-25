package com.haxon.remotelogmonitor.controller.v1.admin;

import com.haxon.remotelogmonitor.dto.user.CreateUserRequestDto;
import com.haxon.remotelogmonitor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        var user = userService.createUser(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created : " + user.getId());
    }
}
