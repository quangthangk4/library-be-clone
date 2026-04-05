package com.library.auth.infrastructure.persistence.mapper;

import com.library.auth.domain.entity.RefreshTokens;
import com.library.auth.domain.valueobject.UUIDToken;
import com.library.auth.infrastructure.persistence.entity.RefreshTokensEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface RefreshTokensEntityMapper {

    RefreshTokens toDomain(RefreshTokensEntity refreshTokens);

    @Mapping(target = "uuidToken", source = "id")
    RefreshTokensEntity toEntity(RefreshTokens refreshTokens);

    @ObjectFactory
    default RefreshTokens create(RefreshTokensEntity entity) {
        return new RefreshTokens(
                UUIDToken.of(entity.getUuidToken()),
                entity.getDeviceId(),
                entity.getUserId(),
                entity.getExpiryDate(),
                entity.isRevoked()
        );
    }


    default UUIDToken map(String value) {
        return value == null ? null : UUIDToken.of(value);
    }

    default String map(UUIDToken value) {
        return value == null ? null : value.getValue();
    }
}
