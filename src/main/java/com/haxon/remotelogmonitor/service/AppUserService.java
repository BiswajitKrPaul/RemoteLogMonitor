package com.haxon.remotelogmonitor.service;

import com.haxon.remotelogmonitor.dto.appUser.LoginAppUserRequestDto;
import com.haxon.remotelogmonitor.entities.AppUser;
import com.haxon.remotelogmonitor.enums.PlatformType;
import com.haxon.remotelogmonitor.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Optional<AppUser> loginOrCreate(LoginAppUserRequestDto loginAppUserRequestDto) {
        log.info("LoginOrCreate AppUserService Method Entered");
        log.debug("Request details: {}", loginAppUserRequestDto);
        final AppUser appUser = appUserRepository.findAppUsersByExternalId(loginAppUserRequestDto.id()).orElse(null);
        if (appUser == null) {
            // Creating App User
            log.debug("AppUser: {} not found", loginAppUserRequestDto.id());
            log.info("Creating AppUser");
            final AppUser newAppUser = appUserRepository.save(AppUser.builder().externalId(loginAppUserRequestDto.id())
                    .platform(PlatformType.valueOf(loginAppUserRequestDto.platform().toUpperCase()))
                    .createdAt(LocalDateTime.now()).fullName(loginAppUserRequestDto.fullName()).build());
            log.debug("Created New AppUser: {}", newAppUser);
            log.info("AppUser Registered Successfully");
            log.info("LoginOrCreate AppUserService Method Exited");
            return Optional.of(newAppUser);
        }
        log.info("LoginOrCreate AppUserService Method Exited");
        return Optional.of(appUser);
    }
}
