package com.bugtracker.bug_tracker;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
//
//    public static void main(String[] args) {
//        System.out.println(
//                new BCryptPasswordEncoder().encode("password")
//        );
//    }

    public static void main(String[] args) {
        System.out.println(
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                        .encode("password")
        );
    }
}