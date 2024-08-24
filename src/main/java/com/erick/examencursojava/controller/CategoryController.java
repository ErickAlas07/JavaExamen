package com.erick.examencursojava.controller;

import com.erick.examencursojava.entity.Category;
import com.erick.examencursojava.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "List all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public List<Category> list() {
        return categoryService.findAll();
    }

    @Operation(summary = "Get category by ID", description = "Returns a category based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Category> opCategory = categoryService.findById(id);
        if (opCategory.isPresent()) {
            return ResponseEntity.ok(opCategory.orElseThrow());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: " + id);
    }

    @Operation(summary = "Register a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request. Bad Syntax."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    @Operation(summary = "Update a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request. Bad Syntax."),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Category category, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Category> opCategory = categoryService.findById(id);
        if (opCategory.isPresent()) {
            Category categoryDb = opCategory.orElseThrow();
            categoryDb.setName(category.getName());
            categoryDb.setType(category.getType());
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryDb));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: " + id);
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Category> opCategory = categoryService.delete(id);
        if (opCategory.isPresent()) {
            return ResponseEntity.ok(opCategory.orElseThrow());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: " + id);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), "The field " + error.getField() + " " + error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
