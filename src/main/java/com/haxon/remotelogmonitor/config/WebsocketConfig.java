package com.haxon.remotelogmonitor.config;

import com.haxon.remotelogmonitor.interceptors.SubscriptionSecurityInterceptor;
import com.haxon.remotelogmonitor.interceptors.WebSocketConnectionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketConnectionInterceptor webSocketConnectionInterceptor;
    private final SubscriptionSecurityInterceptor subscriptionSecurityInterceptor;

    public WebsocketConfig(WebSocketConnectionInterceptor webSocketConnectionInterceptor,
                           SubscriptionSecurityInterceptor subscriptionSecurityInterceptor) {
        this.webSocketConnectionInterceptor = webSocketConnectionInterceptor;
        this.subscriptionSecurityInterceptor = subscriptionSecurityInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketConnectionInterceptor, subscriptionSecurityInterceptor);
    }
}
