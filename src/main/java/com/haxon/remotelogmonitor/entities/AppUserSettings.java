package com.haxon.remotelogmonitor.entities;

import com.haxon.remotelogmonitor.enums.LogLevel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_user_settings")
public class AppUserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID appUserId;

    @OneToOne
    @MapsId
    private AppUser appUser;

    private boolean remoteLoggingEnabled = false;
    private int minLogLevel = LogLevel.INFO.getValue(); // INFO
    private LocalDateTime updatedAt = LocalDateTime.now();
}

