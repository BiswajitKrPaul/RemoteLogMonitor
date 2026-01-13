package com.haxon.remotelogmonitor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String email;
    private String passwordHash;
    private String fullName;
    private boolean isActive = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
