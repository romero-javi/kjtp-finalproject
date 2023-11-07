package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.ProductDTO;
import com.javi.kjtpfinalproject.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    static final String PRODUCTS_PATH = "/api/v1/products";
    static final String PRODUCTS_ID_PATH = PRODUCTS_PATH + "/{productId}";
    private final ProductService productService;

    /*
        Method for admin to add products
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping (PRODUCTS_PATH)
    public ResponseEntity<ProductDTO> addNewProduct(@RequestBody @Valid ProductDTO productDto) throws URISyntaxException {
        ProductDTO createdProduct = productService.addNewProduct(productDto);
        String location = PRODUCTS_PATH.concat("/%s".formatted(createdProduct.productId()));

        return ResponseEntity.created(new URI(location)).body(createdProduct);
    }

    /*
        Method for users to update their profile
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(PRODUCTS_ID_PATH)
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") String productId, @RequestBody @Valid ProductDTO product) {
        productService.updateProductById(productId, product);

        return ResponseEntity.noContent().build();
    }

    /*
        Method for admin to delete products
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(PRODUCTS_ID_PATH)
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users search product
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(PRODUCTS_ID_PATH)
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    /*
        Method for users to view products
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(PRODUCTS_PATH)
    public ResponseEntity<List<ProductDTO>> viewAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }
}
