package com.library.user.domain.service;

public interface IPasswordHasher {
    String hash(String password);
    boolean matches(String passwordRaw, String passwordHash);
}
