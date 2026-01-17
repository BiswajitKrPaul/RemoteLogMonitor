package com.haxon.remotelogmonitor.repositories;

import com.haxon.remotelogmonitor.entities.APIKeys;
import com.haxon.remotelogmonitor.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface APIKeyRepository extends JpaRepository<APIKeys, UUID> {
    APIKeys findAPIKeysByAppUser(AppUser appUser);
}
