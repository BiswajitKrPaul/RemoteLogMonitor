package com.haxon.remotelogmonitor.controller.v1;

import com.haxon.remotelogmonitor.dto.appUser.LoginAppUserRequestDto;
import com.haxon.remotelogmonitor.dto.appUser.LoginAppUserResponseDto;
import com.haxon.remotelogmonitor.entities.AppUser;
import com.haxon.remotelogmonitor.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/device")
public class AppUserControllers {

    private final AppUserService appUserService;

    public AppUserControllers(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @PostMapping
    private ResponseEntity<?> findAppUserByExternalId(@Valid @RequestBody LoginAppUserRequestDto loginAppUserRequestDto) {
        final Optional<AppUser> user = appUserService.loginOrCreate(loginAppUserRequestDto);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new LoginAppUserResponseDto(user.get()
                            .getId()
                            .toString()));
        } else {
            return ResponseEntity.badRequest()
                    .build();
        }
    }
}
