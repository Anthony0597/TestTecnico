package com.reto.programacion.security;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlackList {
    private static final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
