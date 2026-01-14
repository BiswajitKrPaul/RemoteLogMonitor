package com.haxon.remotelogmonitor.repositories;

import com.haxon.remotelogmonitor.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findAppUsersByExternalId(String externalId);
}
