package io.github.com.springsecuritydemo.controllers;

import io.github.com.springsecuritydemo.entities.Product;
import io.github.com.springsecuritydemo.exceptions.CannotUpdateProductException;
import io.github.com.springsecuritydemo.exceptions.ProductAlreadyExistsException;
import io.github.com.springsecuritydemo.exceptions.ProductNotFoundException;
import io.github.com.springsecuritydemo.interfaces.ProductService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService service;

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public List<Product> findAll() {
        return this.service.findAll();
    }

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            var product = this.service.findById(id);
            return ResponseEntity.ok().body(product);
        } catch (ProductNotFoundException err) {
            return ResponseEntity.internalServerError().body(err.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        try {
            var createdProduct = this.service.create(product);
            return ResponseEntity.created(URI.create("/products/" + product.getId())).body(createdProduct);
        } catch (ProductAlreadyExistsException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_MANAGE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product productToUpdate, @PathVariable("id") Long id) {
        try {
            var updatedProduct = this.service.update(productToUpdate, id);
            return ResponseEntity.ok().body(updatedProduct);
        } catch (ProductNotFoundException err) {
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            this.service.delete(id);
        } catch (ProductNotFoundException err) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }
}
