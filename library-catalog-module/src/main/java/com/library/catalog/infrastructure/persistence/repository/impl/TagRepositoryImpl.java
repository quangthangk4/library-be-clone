package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.entities.Tag;
import com.library.catalog.domain.repository.TagRepository;
import com.library.catalog.domain.valueobject.TagId;
import com.library.catalog.infrastructure.persistence.mapper.TagEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.TagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final TagJpaRepository jpaRepository;
    private final TagEntityMapper entityMapper;

    @Override
    public Tag save(Tag tag) {
        var entity = entityMapper.toEntity(tag);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Tag> findById(TagId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        return jpaRepository.findByTagName(tagName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Tag> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Tag> findByIds(List<TagId> ids) {
        List<Long> longIds = ids.stream()
            .map(TagId::getValue)
            .collect(Collectors.toList());
        return jpaRepository.findAllById(longIds).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNameIgnoreCase(String tagName) {
        return jpaRepository.existsByTagNameIgnoreCase(tagName);
    }

    @Override
    public void delete(Tag tag) {
        jpaRepository.deleteById(tag.getId().getValue());
    }

    @Override
    public void deleteById(TagId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
