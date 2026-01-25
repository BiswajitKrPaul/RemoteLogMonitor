package com.haxon.remotelogmonitor.service;

import com.haxon.remotelogmonitor.config.ApiKeyHasher;
import com.haxon.remotelogmonitor.dto.apiKeys.APIKeyResponseDTO;
import com.haxon.remotelogmonitor.entities.APIKeys;
import com.haxon.remotelogmonitor.entities.AppUser;
import com.haxon.remotelogmonitor.repositories.APIKeyRepository;
import com.haxon.remotelogmonitor.repositories.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class APIKeyService {

    private final APIKeyRepository apiKeyRepository;
    private final ApiKeyHasher passwordEncoder;
    private final AppUserRepository userRepository;

    public APIKeyService(APIKeyRepository apiKeyRepository, ApiKeyHasher passwordEncoder,
                         AppUserRepository userRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public APIKeyResponseDTO createApiKey(String userID) {
        final AppUser user = userRepository.findAppUsersByExternalId(userID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        String createdKey = UUID.randomUUID().toString().concat(".").concat(UUID.randomUUID().toString());
        APIKeys keys = apiKeyRepository.save(
                APIKeys.builder().hashedAPIKey(passwordEncoder.hash(createdKey)).appUser(user)
                        .createdAt(LocalDateTime.now()).build());
        return APIKeyResponseDTO.builder().id(keys.getId().toString()).apiKey(createdKey).build();
    }

    public boolean revokeApiKey(String id) {
        final Optional<APIKeys> key = apiKeyRepository.findById(UUID.fromString(id));
        if (key.isPresent() && !key.get().isRevoked()) {
            final APIKeys apiKey = key.get();
            apiKey.setRevoked(true);
            apiKeyRepository.save(apiKey);
            return true;
        }
        return false;
    }
}
