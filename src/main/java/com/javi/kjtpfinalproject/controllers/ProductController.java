package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;
import com.javi.kjtpfinalproject.services.ProductService;
import com.javi.kjtpfinalproject.shared.constants.ControllerPaths;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /*
        Method for admin to add a product
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(ControllerPaths.PRODUCTS)
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductDTO productDto) throws URISyntaxException {
        ProductDTO createdProduct = productService.addNewProduct(productDto);
        String location = ControllerPaths.PRODUCTS.concat(
                String.format("/%s", createdProduct.productId())
        );

        return ResponseEntity.created(new URI(location)).body(createdProduct);
    }

    /*
        Method for admin to update a product
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(ControllerPaths.PRODUCT_ID)
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") String productId,
                                              @RequestBody @Valid ProductDTO product) {
        productService.updateProductById(productId, product);

        return ResponseEntity.noContent().build();
    }

    /*
        Method for admin to delete products
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ControllerPaths.PRODUCT_ID)
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users search product
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.PRODUCT_ID)
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    /*
        Method for users to view products
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.PRODUCTS)
    public ResponseEntity<Page<ProductDTO>> viewAllProducts(@RequestParam(required = false) Integer pageNumber,
                                                            @RequestParam(required = false) Integer pageSize) {
        return ResponseEntity.ok(productService.findAll(pageNumber, pageSize));
    }
}
