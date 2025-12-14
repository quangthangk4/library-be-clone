package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.entities.Author;
import com.library.catalog.domain.repository.AuthorRepository;
import com.library.catalog.domain.valueobject.AuthorId;
import com.library.catalog.infrastructure.persistence.mapper.AuthorEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.AuthorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaRepository jpaRepository;
    private final AuthorEntityMapper entityMapper;

    @Override
    public Author save(Author author) {
        var entity = entityMapper.toEntity(author);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Author> findById(AuthorId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Author> findByName(String authorName) {
        return jpaRepository.findByAuthorName(authorName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Author> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Author> findByIds(List<AuthorId> ids) {
        List<Long> longIds = ids.stream()
            .map(AuthorId::getValue)
            .collect(Collectors.toList());
        return jpaRepository.findAllById(longIds).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Author> searchByName(String nameKeyword) {
        return jpaRepository.searchByName(nameKeyword).stream()
                .map(entityMapper::toDomainModel)
                .toList();
    }

    @Override
    public boolean existsByName(String authorName) {
        return jpaRepository.existsByAuthorName(authorName);
    }

    @Override
    public void delete(Author author) {
        jpaRepository.deleteById(author.getId().getValue());
    }

    @Override
    public void deleteById(AuthorId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
