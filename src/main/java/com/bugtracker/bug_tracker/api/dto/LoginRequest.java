package com.bugtracker.bug_tracker.api.dto;


public record LoginRequest(
        String email,
        String password
) {}
