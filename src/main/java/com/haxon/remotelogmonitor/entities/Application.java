package com.haxon.remotelogmonitor.entities;

import com.haxon.remotelogmonitor.enums.PlatformType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Application {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    private int logRetentionDays = 7; // default retention
    private LocalDateTime createdAt = LocalDateTime.now();
}