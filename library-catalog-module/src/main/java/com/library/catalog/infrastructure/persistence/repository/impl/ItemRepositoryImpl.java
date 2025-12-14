package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.entities.Item;
import com.library.catalog.domain.entities.ItemStatus;
import com.library.catalog.domain.repository.ItemRepository;
import com.library.catalog.domain.valueobject.Barcode;
import com.library.catalog.domain.valueobject.ItemId;
import com.library.catalog.domain.valueobject.PublicationId;
import com.library.catalog.infrastructure.persistence.mapper.ItemEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository jpaRepository;
    private final ItemEntityMapper entityMapper;

    @Override
    public Item save(Item item) {
        var entity = entityMapper.toEntity(item);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Item> findById(ItemId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Item> findByBarcode(Barcode barcode) {
        return jpaRepository.findByBarcode(barcode.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Item> findByPublicationId(PublicationId publicationId) {
        return jpaRepository.findByPublicationId(publicationId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Item> findByStatus(ItemStatus status) {
        return jpaRepository.findByStatus(status).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Item> findAvailableByPublicationId(PublicationId publicationId) {
        return jpaRepository.findAvailableByPublicationId(publicationId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByBarcode(Barcode barcode) {
        return jpaRepository.existsByBarcode(barcode.getValue());
    }

    @Override
    public void delete(Item item) {
        jpaRepository.deleteById(item.getId().getValue());
    }

    @Override
    public long countByPublicationId(PublicationId publicationId) {
        return jpaRepository.countByPublicationId(publicationId.getValue());
    }

    @Override
    public long countAvailableByPublicationId(PublicationId publicationId) {
        return jpaRepository.countAvailableByPublicationId(publicationId.getValue());
    }

    @Override
    public List<Item> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(ItemId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public boolean existsById(ItemId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
