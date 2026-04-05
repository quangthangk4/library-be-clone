package com.library.auth.infrastructure.persistence.repository;

import com.library.auth.infrastructure.persistence.entity.RefreshTokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface RefreshTokensJpaRepository extends JpaRepository<RefreshTokensEntity, String> {
    List<RefreshTokensEntity> findAllByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM RefreshTokensEntity t WHERE t.expiryDate < :now")
    int deleteExpiredTokens(@Param("now") Instant now);

    void deleteByUserIdAndDeviceId(Long userId, String deviceId);

    @Modifying
    @Query("UPDATE RefreshTokensEntity t SET t.revoked = true WHERE t.userId = :userId")
    void revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE RefreshTokensEntity t SET t.revoked = true WHERE t.uuidToken = :uuidToken")
    void revokeToken(@Param("uuidToken") String uuidToken);
}
