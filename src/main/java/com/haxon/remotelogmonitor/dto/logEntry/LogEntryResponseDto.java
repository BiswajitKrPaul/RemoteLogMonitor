package com.haxon.remotelogmonitor.dto.logEntry;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record LogEntryResponseDto(@NotBlank String userId, @NotBlank String loglevel, @NotBlank String message,
                                  String stacktrace, @NotBlank String platform, @NotBlank String id,
                                  @NotBlank LocalDateTime loggedAt) {
}
