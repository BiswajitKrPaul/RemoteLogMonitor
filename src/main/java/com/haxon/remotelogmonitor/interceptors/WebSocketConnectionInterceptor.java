package com.haxon.remotelogmonitor.interceptors;

import com.haxon.remotelogmonitor.config.ApiKeyHasher;
import com.haxon.remotelogmonitor.entities.APIKeys;
import com.haxon.remotelogmonitor.entities.AppUser;
import com.haxon.remotelogmonitor.repositories.APIKeyRepository;
import com.haxon.remotelogmonitor.repositories.AppUserRepository;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

@Component
public class WebSocketConnectionInterceptor implements ChannelInterceptor {

    final ApiKeyHasher apiKeyHasher;
    final APIKeyRepository apiKeyRepository;
    final AppUserRepository appUserRepository;

    public WebSocketConnectionInterceptor(ApiKeyHasher apiKeyHasher, APIKeyRepository apiKeyRepository,
                                          AppUserRepository appUserRepository) {
        this.apiKeyHasher = apiKeyHasher;
        this.apiKeyRepository = apiKeyRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public @Nullable Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        } else if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("X-API-KEY");
            String userID = accessor.getFirstNativeHeader("X-USER-ID");

            if (token == null || token.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "X-API-KEY header is missing");
            }

            final Optional<AppUser> user = appUserRepository.findAppUsersByExternalId(userID);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
            }

            final AppUser appUser = user.get();
            final APIKeys apiKeyForThatUser = apiKeyRepository.findAPIKeysByAppUser(appUser);

            if (apiKeyForThatUser == null || apiKeyForThatUser.isRevoked()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a Valid API Key");
            }

            String hashApiToken = apiKeyHasher.hash(token);

            if (!hashApiToken.equals(apiKeyForThatUser.getHashedAPIKey())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a Valid API Key");
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.get().getExternalId(), null,
                    Collections.emptyList());

            accessor.setUser(authentication);
        }

        return message;
    }
}
