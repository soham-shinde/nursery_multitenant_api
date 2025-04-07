package com.api.nursery_system.util;

import java.security.SecureRandom;

public class HelperMethods {
    private HelperMethods() {
        throw new IllegalStateException("Utility class");
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateTenantId() {
        StringBuilder sb = new StringBuilder("TENANT_");
        for (int i = 0; i < ID_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString().toLowerCase();
    }
}
