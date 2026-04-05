package com.library.user.domain.valueobject;

import com.library.shared.util.StaticVariable;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

/**
 * UserProfile value object
 * Encapsulates user personal information
 */
@Value
@With
public class UserProfile {
    String fullName;
    LocalDate dateOfBirth;
    String phoneNumber;
    String address;
    String profilePictureUrl;

    public UserProfile(String fullName,
                      LocalDate dateOfBirth,
                      String phoneNumber,
                      String address,
                      String profilePictureUrl) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth must be in the past");
        }
        this.fullName = fullName.trim();
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
        this.address = address != null ? address.trim() : null;
        this.profilePictureUrl = profilePictureUrl != null ? profilePictureUrl.trim() : null;
    }

    public static UserProfile create(String fullName, LocalDate dateOfBirth, String phoneNumber, String address) {
        return new UserProfile(fullName, dateOfBirth, phoneNumber, address, StaticVariable.DEFAULT_AVATAR);
    }

    public static UserProfile createWithAvatar(String fullName, String avatarUrl) {
        return new UserProfile(fullName, null, null, null, avatarUrl);
    }
}
