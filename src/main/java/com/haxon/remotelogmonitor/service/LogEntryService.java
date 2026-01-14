package com.haxon.remotelogmonitor.service;

import com.haxon.remotelogmonitor.dto.logEntry.LogEntryRequestDto;
import com.haxon.remotelogmonitor.entities.AppUser;
import com.haxon.remotelogmonitor.entities.LogEntry;
import com.haxon.remotelogmonitor.enums.LogLevel;
import com.haxon.remotelogmonitor.enums.PlatformType;
import com.haxon.remotelogmonitor.repositories.AppUserRepository;
import com.haxon.remotelogmonitor.repositories.LogEntryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LogEntryService {

    private final LogEntryRepository logEntryRepository;
    private final AppUserRepository appUserRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public LogEntryService(LogEntryRepository logEntryRepository, AppUserRepository appUserRepository,
                           SimpMessagingTemplate simpMessagingTemplate) {
        this.logEntryRepository = logEntryRepository;
        this.appUserRepository = appUserRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public LogEntry createLog(LogEntryRequestDto logEntryRequestDto) {
        final Optional<AppUser> appUser = appUserRepository.findAppUsersByExternalId(logEntryRequestDto.userId());
        if (appUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        final LogEntry createdLog = logEntryRepository.save(LogEntry.builder()
                .logLevel(LogLevel.valueOf(logEntryRequestDto.loglevel()
                        .toUpperCase()))
                .message(logEntryRequestDto.message())
                .stacktrace(logEntryRequestDto.stacktrace())
                .platform(PlatformType.valueOf(logEntryRequestDto.platform()
                        .toUpperCase()))
                .createdAt(LocalDateTime.now())
                .appUser(appUser.get())
                .build());
        simpMessagingTemplate.convertAndSend("/topic/user.logs/" + appUser.get()
                .getExternalId(), createdLog);
        return createdLog;
    }
}
