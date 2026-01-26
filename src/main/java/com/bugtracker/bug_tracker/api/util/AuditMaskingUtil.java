package com.bugtracker.bug_tracker.api.util;


import com.bugtracker.bug_tracker.domain.enums.Role;

public final class AuditMaskingUtil {

    private AuditMaskingUtil() {}

    // STATUS / VALUE masking
    public static String maskValue(
            String value,
            Role viewerRole
    ) {
        if (viewerRole == Role.REPORTER) {
            return "****";
        }
        return value;
    }

    // ROLE masking
    public static String maskActorRole(
            String actorRole,
            Role viewerRole
    ) {
        if (viewerRole == Role.ADMIN) {
            return actorRole;
        }
        if (viewerRole == Role.DEVELOPER || viewerRole == Role.TESTER) {
            return actorRole; // role is okay
        }
        return null; // reporter sees nothing
    }

    // ID masking (most sensitive)
    public static Long maskActorId(
            Long actorId,
            Role viewerRole
    ) {
        if (viewerRole == Role.ADMIN) {
            return actorId;
        }
        return null;
    }
}
