package com.javi.kjtpfinalproject.shared.utils;

import com.javi.kjtpfinalproject.shared.exceptions.InvalidUUID;

import java.util.UUID;

public class UUIDValidator {
    private UUIDValidator() {
    }

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            throw new InvalidUUID("Invalid UUID sent in the request");
        }
    }
}
