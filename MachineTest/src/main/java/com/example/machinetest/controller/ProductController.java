package com.example.machinetest.controller;

import com.example.machinetest.model.Product;
import com.example.machinetest.service.CategoryService;
import com.example.machinetest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id)
                    .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(category -> productService.getProductsByCategory(category))
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.saveProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        if (productService.getProductById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {

        Product existingProduct = productService.getProductById(id).orElse(null);

        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductPrice(updatedProduct.getProductPrice());
        existingProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());

        Product updatedProductEntity = productService.saveProduct(existingProduct);

        return new ResponseEntity<>(updatedProductEntity, HttpStatus.OK);
    }
}
