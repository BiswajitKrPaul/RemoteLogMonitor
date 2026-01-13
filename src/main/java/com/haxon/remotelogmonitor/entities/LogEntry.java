package com.haxon.remotelogmonitor.entities;

import com.haxon.remotelogmonitor.enums.LogLevel;
import com.haxon.remotelogmonitor.enums.PlatformType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    private String message;
    @Column(columnDefinition = "jsonb")
    private String metadata;

    private LocalDateTime createdAt = LocalDateTime.now();
}
