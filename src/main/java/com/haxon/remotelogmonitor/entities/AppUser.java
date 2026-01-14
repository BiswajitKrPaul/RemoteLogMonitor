package com.haxon.remotelogmonitor.entities;


import com.haxon.remotelogmonitor.enums.PlatformType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 1024)
    private String fullName;

    @Column(unique = true)
    private String externalId; // device/user id

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    private LocalDateTime createdAt = LocalDateTime.now();
}
