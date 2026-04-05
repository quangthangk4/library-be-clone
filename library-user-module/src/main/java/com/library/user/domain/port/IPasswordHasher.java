package com.library.user.domain.port;

public interface IPasswordHasher {
    String hash(String password);
    boolean matches(String passwordRaw, String passwordHash);
}
