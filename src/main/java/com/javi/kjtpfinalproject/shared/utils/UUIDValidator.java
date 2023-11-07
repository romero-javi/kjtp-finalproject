package com.javi.kjtpfinalproject.shared.utils;

import com.javi.kjtpfinalproject.shared.exceptions.InvalidDataException;

import java.util.UUID;

public class UUIDValidator {
    private UUIDValidator() {
    }

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException("Invalid UUID: '%s'".formatted(uuid));
        }
    }
}
