package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.application.dto.request.GetAllPublicationForLibrarian;
import com.library.catalog.domain.entities.Publication;
import com.library.catalog.domain.repository.PublicationRepository;
import com.library.catalog.domain.valueobject.*;
import com.library.catalog.infrastructure.persistence.mapper.PublicationEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.PublicationJpaRepository;
import com.library.catalog.infrastructure.persistence.specification.PublicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PublicationRepositoryImpl implements PublicationRepository {

    private final PublicationJpaRepository jpaRepository;
    private final PublicationEntityMapper entityMapper;

    @Override
    public Publication save(Publication publication) {
        var entity = entityMapper.toEntity(publication);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Publication> findById(PublicationId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Publication> findByISBN(ISBN isbn) {
        return jpaRepository.findByIsbn(isbn.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Publication> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public Page<Publication> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Publication> findByAuthorId(AuthorId authorId) {
        return jpaRepository.findByAuthorId(authorId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Publication> findByCategoryId(CategoryId categoryId) {
        return jpaRepository.findByCategoryId(categoryId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Publication> findByPublisherId(PublisherId publisherId) {
        return jpaRepository.findByPublisherId(publisherId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByISBN(ISBN isbn) {
        return jpaRepository.existsByIsbn(isbn.getValue());
    }

    @Override
    public void delete(Publication publication) {
        jpaRepository.deleteById(publication.getId().getValue());
    }

    @Override
    public void deleteById(PublicationId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public List<Publication> findByIds(List<PublicationId> ids) {
        List<Long> longIds = ids.stream()
            .map(PublicationId::getValue)
            .collect(Collectors.toList());
        return jpaRepository.findAllById(longIds).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Publication> findByTagId(TagId tagId) {
        return jpaRepository.findByTagId(tagId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }


    @Override
    public Page<Publication> getAllPublicationForLibrarian(GetAllPublicationForLibrarian request, Pageable pageable) {
        return jpaRepository.findAll(
                PublicationSpecification.buildSpecification(request),
                pageable
            )
            .map(entityMapper::toDomainModel);
    }

    @Override
    public boolean existsById(PublicationId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
