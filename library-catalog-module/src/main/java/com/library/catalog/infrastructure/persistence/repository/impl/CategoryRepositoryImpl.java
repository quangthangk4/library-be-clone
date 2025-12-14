package com.library.catalog.infrastructure.persistence.repository.impl;

import com.library.catalog.domain.entities.Category;
import com.library.catalog.domain.repository.CategoryRepository;
import com.library.catalog.domain.valueobject.CategoryId;
import com.library.catalog.infrastructure.persistence.mapper.CategoryEntityMapper;
import com.library.catalog.infrastructure.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryEntityMapper entityMapper;

    @Override
    public Category save(Category category) {
        var entity = entityMapper.toEntity(category);
        var saved = jpaRepository.save(entity);
        return entityMapper.toDomainModel(saved);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return jpaRepository.findById(id.getValue())
            .map(entityMapper::toDomainModel);
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        return jpaRepository.findByCategoryName(categoryName)
            .map(entityMapper::toDomainModel);
    }

    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Category> findByIds(List<CategoryId> ids) {
        List<Long> longIds = ids.stream()
            .map(CategoryId::getValue)
            .collect(Collectors.toList());
        return jpaRepository.findAllById(longIds).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Category> findByParentCategoryId(CategoryId parentId) {
        return jpaRepository.findByParentCategoryId(parentId.getValue()).stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Category> findRootCategories() {
        return jpaRepository.findByParentCategoryIdIsNull().stream()
            .map(entityMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String categoryName) {
        return jpaRepository.existsByCategoryName(categoryName);
    }

    @Override
    public boolean hasChildren(CategoryId id) {
        return jpaRepository.countByParentCategoryId(id.getValue()) > 0;
    }

    @Override
    public void delete(Category category) {
        jpaRepository.deleteById(category.getId().getValue());
    }

    @Override
    public void deleteById(CategoryId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
