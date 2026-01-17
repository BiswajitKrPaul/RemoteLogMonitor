package com.haxon.remotelogmonitor.interceptors;

import com.haxon.remotelogmonitor.config.ApiKeyHasher;
import com.haxon.remotelogmonitor.repositories.APIKeyRepository;
import com.haxon.remotelogmonitor.repositories.AppUserRepository;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class SubscriptionSecurityInterceptor implements ChannelInterceptor {

    final ApiKeyHasher apiKeyHasher;
    final APIKeyRepository apiKeyRepository;
    final AppUserRepository appUserRepository;

    public SubscriptionSecurityInterceptor(ApiKeyHasher apiKeyHasher, APIKeyRepository apiKeyRepository,
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
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();

            if (destination == null) {
                throw new IllegalArgumentException("Cannot subscribe to null destination");
            }

            final String userId = getUserId(destination);
            final Authentication authentication = (Authentication) accessor.getUser();

            if (authentication == null) {
                throw new IllegalArgumentException("Please Authenticate before subscribing");
            }

            String authenticatedUserId = authentication.getName();

            if (!authenticatedUserId.equals(userId)) {
                throw new IllegalArgumentException("Cannot subscribe to different users");
            }
            return message;
        }

        return message;
    }


    private String getUserId(String destination) {
        String[] destinationParts = destination.split("/");
        return destinationParts[destinationParts.length - 1];
    }
}
