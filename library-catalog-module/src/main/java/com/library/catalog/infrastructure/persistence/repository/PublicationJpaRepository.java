package com.library.catalog.infrastructure.persistence.repository;

import com.library.catalog.infrastructure.persistence.entity.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationJpaRepository extends JpaRepository<PublicationEntity, Long>{

}
