package io.github.com.springsecuritydemo.interfaces;

import io.github.com.springsecuritydemo.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product create(Product product);
    Product findById(Long id);
    Product update(Product product, Long id);
    void delete(Long id);
}
