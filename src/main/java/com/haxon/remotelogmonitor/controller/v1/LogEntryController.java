package com.haxon.remotelogmonitor.controller.v1;

import com.haxon.remotelogmonitor.dto.logEntry.LogEntryRequestDto;
import com.haxon.remotelogmonitor.dto.logEntry.LogEntryResponseDto;
import com.haxon.remotelogmonitor.entities.LogEntry;
import com.haxon.remotelogmonitor.service.LogEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/logs")
public class LogEntryController {

    private final LogEntryService logEntryService;

    public LogEntryController(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @PostMapping()
    public ResponseEntity<?> createLogs(@Valid @RequestBody LogEntryRequestDto logEntryRequestDto) {
        final LogEntry createdLog = logEntryService.createLog(logEntryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LogEntryResponseDto.builder()
                        .platform(createdLog.getPlatform()
                                .name())
                        .id(createdLog.getId()
                                .toString())
                        .loggedAt(createdLog.getCreatedAt())
                        .message(createdLog.getMessage())
                        .stacktrace(createdLog.getStacktrace())
                        .loglevel(createdLog.getLogLevel()
                                .name())
                        .userId(createdLog.getAppUser()
                                .getId()
                                .toString())
                        .build());
    }
}
