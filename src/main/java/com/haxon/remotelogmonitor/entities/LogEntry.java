package com.haxon.remotelogmonitor.entities;

import com.haxon.remotelogmonitor.enums.LogLevel;
import com.haxon.remotelogmonitor.enums.PlatformType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "logs")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "application_id")
//    private Application application;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    @Column(columnDefinition = "text")
    private String message;

    @Column(columnDefinition = "text")
    private String stacktrace;


    private LocalDateTime createdAt = LocalDateTime.now();
}
