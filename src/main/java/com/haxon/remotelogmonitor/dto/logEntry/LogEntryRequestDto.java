package com.haxon.remotelogmonitor.dto.logEntry;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Map;

@Builder
public record LogEntryRequestDto(@NotBlank String userId, @NotBlank String loglevel, @NotBlank String message,
                                 String stacktrace, @NotBlank String platform) {
}
