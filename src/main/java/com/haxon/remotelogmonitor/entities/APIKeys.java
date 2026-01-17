package com.haxon.remotelogmonitor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "api_keys")
public class APIKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, updatable = false, name = "hashed_api_key")
    private String hashedAPIKey;

    @Column(name = "is_revoked")
    private boolean isRevoked = false;

    private LocalDateTime createdAt;

    @OneToOne
    private AppUser appUser;
}
