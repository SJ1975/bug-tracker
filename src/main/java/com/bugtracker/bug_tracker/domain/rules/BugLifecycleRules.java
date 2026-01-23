package com.bugtracker.bug_tracker.domain.rules;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;

import java.util.Set;


public final class BugLifecycleRules {

    public static void validate(
            BugStatus current,
            BugStatus next,
            Role actorRole
    ) {
        if (current == next) {
            throw new IllegalStateException(
                    "Bug already in status " + current
            );
        }

        // ADMIN bypasses all lifecycle rules
        if (actorRole == Role.ADMIN) {
            return;
        }

        switch (actorRole) {

            case DEVELOPER -> {
                if (current == BugStatus.OPEN &&
                        next == BugStatus.IN_PROGRESS) return;

                if (current == BugStatus.REOPEN &&
                        next == BugStatus.IN_PROGRESS) return;

                if (current == BugStatus.IN_PROGRESS &&
                        next == BugStatus.READY_FOR_TEST) return;
            }

            case TESTER -> {
                if (current == BugStatus.READY_FOR_TEST &&
                        next == BugStatus.SOLVED) return;

                if (current == BugStatus.READY_FOR_TEST &&
                        next == BugStatus.REOPEN) return;
            }

            case REPORTER -> {
                throw new IllegalStateException(
                        "REPORTER cannot change bug status"
                );
            }
        }

        throw new IllegalStateException(
                "Role " + actorRole +
                        " cannot change status from " +
                        current + " to " + next
        );
    }

    private BugLifecycleRules() {}
}