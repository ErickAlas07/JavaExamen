package com.erick.examencursojava.service;

import com.erick.examencursojava.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Optional<Category> findById(Long id);

    Optional <Category> update(Long id, Category category);

    Optional <Category> delete(Long id);
}
