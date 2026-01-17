package com.haxon.remotelogmonitor.dto.apiKeys;

import lombok.Builder;

@Builder
public record APIKeyResponseDTO(String apiKey, String id) {
}
