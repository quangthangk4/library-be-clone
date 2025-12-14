package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.entities.Publisher;
import com.library.catalog.domain.repository.PublisherRepository;
import com.library.catalog.domain.valueobject.PublisherId;
import com.library.catalog.infrastructure.persistence.mapper.PublisherEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.PublisherJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PublisherRepositoryImpl implements PublisherRepository {

    private final PublisherJpaRepository jpaRepository;
    private final PublisherEntityMapper entityMapper;

    @Override
    public Publisher save(Publisher publisher) {
        var entity = entityMapper.toEntity(publisher);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Publisher> findById(PublisherId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Publisher> findByName(String publisherName) {
        return jpaRepository.findByPublisherName(publisherName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Publisher> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String publisherName) {
        return jpaRepository.existsByPublisherName(publisherName);
    }

    @Override
    public void delete(Publisher publisher) {
        jpaRepository.deleteById(publisher.getId().getValue());
    }

    @Override
    public void deleteById(PublisherId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
