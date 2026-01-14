package com.haxon.remotelogmonitor.dto.appUser;

import jakarta.validation.constraints.NotBlank;

public record LoginAppUserRequestDto(@NotBlank String platform, @NotBlank String fullName, @NotBlank String id) {
}
