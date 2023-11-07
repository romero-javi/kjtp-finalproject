package com.javi.kjtpfinalproject.product.service;

import com.javi.kjtpfinalproject.product.dto.ProductRegistrationDto;
import com.javi.kjtpfinalproject.product.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(String productID);
    ProductResponseDto addNewProduct(ProductRegistrationDto newProduct);
    void updateProductById(String productID, ProductRegistrationDto newData);
    void deleteById(String productID);
}
