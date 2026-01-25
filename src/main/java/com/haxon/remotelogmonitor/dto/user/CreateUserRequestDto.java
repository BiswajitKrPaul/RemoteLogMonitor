package com.haxon.remotelogmonitor.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotBlank
    @Email
    private String email;

    private String password;

    @NotBlank
    @JsonProperty("full_name")
    private String fullName;
}
