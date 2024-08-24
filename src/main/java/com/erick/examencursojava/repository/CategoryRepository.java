package com.erick.examencursojava.repository;

import com.erick.examencursojava.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    boolean existsByName(String name);
}
