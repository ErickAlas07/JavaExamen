package com.erick.examencursojava.service.implementations;

import com.erick.examencursojava.entity.Category;
import com.erick.examencursojava.entity.Customer;
import com.erick.examencursojava.repository.CategoryRepository;
import com.erick.examencursojava.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category save(Category category) {
        boolean exists = categoryRepository.existsByName(category.getName());
        if(exists){
            throw new IllegalArgumentException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Category> update(Long id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            Category categorydb = optionalCategory.orElseThrow();

            categorydb.setName(category.getName());
            categorydb.setType(category.getType());
            return Optional.of(categoryRepository.save(categorydb));
        }
        return optionalCategory;
    }

    @Override
    @Transactional
    public Optional<Category> delete(Long id) {
        Optional<Category> opCategory = categoryRepository.findById(id);
        opCategory.ifPresent(db -> {
            categoryRepository.delete(db);
        });
        return opCategory;
    }
}
