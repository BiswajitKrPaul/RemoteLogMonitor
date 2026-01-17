package com.haxon.remotelogmonitor.controller.v1;

import com.haxon.remotelogmonitor.dto.apiKeys.APIKeyResponseDTO;
import com.haxon.remotelogmonitor.service.APIKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/keys")
public class APIKeyController {

    private final APIKeyService apiKeyService;

    public APIKeyController(APIKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @GetMapping("/{userID}")
    public ResponseEntity<?> createNewApiKey(@PathVariable String userID) {
        final APIKeyResponseDTO createKey = apiKeyService.createApiKey(userID);
        return ResponseEntity.ok(createKey);
    }
}
