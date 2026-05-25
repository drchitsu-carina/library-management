package com.library.service;

import com.library.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getById(Long id);

    Category save(Category category);

    void delete(Long id);
}