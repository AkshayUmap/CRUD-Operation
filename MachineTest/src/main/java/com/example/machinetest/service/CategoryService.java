package com.example.machinetest.service;

import com.example.machinetest.model.Category;
import com.example.machinetest.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepository;

    @Autowired
    public CategoryService(CategoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
