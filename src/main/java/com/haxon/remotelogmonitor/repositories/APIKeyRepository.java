package com.haxon.remotelogmonitor.repositories;

import com.haxon.remotelogmonitor.entities.APIKeys;
import com.haxon.remotelogmonitor.entities.AppUser;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface APIKeyRepository extends JpaRepository<@NonNull APIKeys, @NonNull UUID> {
    APIKeys findAPIKeysByAppUser(AppUser appUser);
}
