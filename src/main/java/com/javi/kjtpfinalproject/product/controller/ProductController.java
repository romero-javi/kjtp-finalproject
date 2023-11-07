package com.javi.kjtpfinalproject.product.controller;

import com.javi.kjtpfinalproject.product.dto.ProductRegistrationDto;
import com.javi.kjtpfinalproject.product.dto.ProductResponseDto;
import com.javi.kjtpfinalproject.product.service.ProductService;
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
    static final String BASE_URI = "/api/v1/products";
    private final ProductService productService;

    /*
        Method for admin to add products
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping (BASE_URI)
    public ResponseEntity<ProductResponseDto> addNewProduct(ProductRegistrationDto productRegistrationDto) throws URISyntaxException {
        ProductResponseDto createdProduct = productService.addNewProduct(productRegistrationDto);
        String location = BASE_URI.concat("/%s".formatted(createdProduct.productId()));

        return ResponseEntity.created(new URI(location)).body(createdProduct);
    }

    /*
        Method for users to update their profile
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping(BASE_URI + "/{productId}")
    public ResponseEntity<Void> updateCustomerProfile(@PathVariable("productId") String productId, @RequestBody ProductRegistrationDto product) {
        productService.updateProductById(productId, product);

        return ResponseEntity.noContent().build();
    }

    /*
        Method for admin to delete products
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(BASE_URI + "/{productId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("productId") String productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users search product
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(BASE_URI + "/{productId}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    /*
        Method for users to view products
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(BASE_URI)
    public ResponseEntity<List<ProductResponseDto>> viewAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }
}
