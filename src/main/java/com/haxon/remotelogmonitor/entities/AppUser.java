package com.haxon.remotelogmonitor.entities;


import com.haxon.remotelogmonitor.enums.PlatformType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    private String externalId; // device/user id

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    private LocalDateTime createdAt = LocalDateTime.now();
}
