package com.example.machinetest.repository;

import com.example.machinetest.model.Category;
import com.example.machinetest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
