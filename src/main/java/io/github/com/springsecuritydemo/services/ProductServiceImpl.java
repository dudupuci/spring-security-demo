package io.github.com.springsecuritydemo.services;

import io.github.com.springsecuritydemo.entities.Product;
import io.github.com.springsecuritydemo.exceptions.CannotUpdateProductException;
import io.github.com.springsecuritydemo.exceptions.ProductAlreadyExistsException;
import io.github.com.springsecuritydemo.exceptions.ProductNotFoundException;
import io.github.com.springsecuritydemo.interfaces.ProductService;
import io.github.com.springsecuritydemo.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Product create(Product product) {
        if (product.getId() != null) {
            throw new ProductAlreadyExistsException("Product already exists!");
        }

        return this.repository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found!"));
    }

    @Override
    public Product update(Product product, Long id) {
        var productToUpdate = this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found!"));
        updateProduct(productToUpdate, product);

        return this.repository.save(productToUpdate);
    }

    @Override
    public void delete(Long id) {
        if (this.repository.findById(id).isEmpty()) {
            throw new ProductNotFoundException("Product with id " + id + " does not exists or is already deleted.");
        }
        this.repository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return this.repository.findAll();
    }

    private void updateProduct(Product productToUpdate, Product product) {
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
    }
}
