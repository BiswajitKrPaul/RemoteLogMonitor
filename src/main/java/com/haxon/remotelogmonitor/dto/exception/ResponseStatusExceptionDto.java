package com.haxon.remotelogmonitor.dto.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ResponseStatusExceptionDto(@JsonProperty("status_code") int rawStatusCode, String reason) {
}
