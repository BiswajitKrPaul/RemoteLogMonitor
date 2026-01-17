package com.haxon.remotelogmonitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class ApiKeyHasher {

    private final Mac mac;

    public ApiKeyHasher(@Value("${api.key.secret}") String secret) {
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(
                    new SecretKeySpec(
                            secret.getBytes(StandardCharsets.UTF_8),
                            "HmacSHA256"
                    )
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String hash(String rawKey) {
        byte[] hmac = mac.doFinal(rawKey.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmac);
    }
}

