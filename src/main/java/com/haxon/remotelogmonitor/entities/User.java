package com.haxon.remotelogmonitor.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {


    private boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String email;
    @Column(name = "password")
    private String passwordHash;
    @Column(length = 500)
    private String fullName;

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    @NonNull
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
