package com.library.user.domain.valueobject;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * UserProfile value object
 * Encapsulates user personal information
 */
@Getter
public class UserProfile {
    private final String fullName;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String address;
    private final String profilePictureUrl;

    public UserProfile(String fullName,
                      LocalDate dateOfBirth,
                      String phoneNumber,
                      String address,
                      String profilePictureUrl) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        this.fullName = fullName.trim();
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber != null ? phoneNumber.trim() : null;
        this.address = address != null ? address.trim() : null;
        this.profilePictureUrl = profilePictureUrl != null ? profilePictureUrl.trim() : null;
    }

    public static UserProfile create(String fullName) {
        return new UserProfile(fullName, null, null, null, null);
    }

    public static UserProfile create(String fullName, LocalDate dateOfBirth, String phoneNumber, String address) {
        return new UserProfile(fullName, dateOfBirth, phoneNumber, address, null);
    }

    public UserProfile withProfilePicture(String profilePictureUrl) {
        return new UserProfile(this.fullName, this.dateOfBirth, this.phoneNumber,
                             this.address, profilePictureUrl);
    }

    public UserProfile withPhoneNumber(String phoneNumber) {
        return new UserProfile(this.fullName, this.dateOfBirth, phoneNumber,
                             this.address, this.profilePictureUrl);
    }

    public UserProfile withAddress(String address) {
        return new UserProfile(this.fullName, this.dateOfBirth, this.phoneNumber,
                             address, this.profilePictureUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(fullName, that.fullName) &&
               Objects.equals(dateOfBirth, that.dateOfBirth) &&
               Objects.equals(phoneNumber, that.phoneNumber) &&
               Objects.equals(address, that.address) &&
               Objects.equals(profilePictureUrl, that.profilePictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, dateOfBirth, phoneNumber, address, profilePictureUrl);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
               "fullName='" + fullName + '\'' +
               ", dateOfBirth=" + dateOfBirth +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", address='" + address + '\'' +
               ", profilePictureUrl='" + profilePictureUrl + '\'' +
               '}';
    }
}
