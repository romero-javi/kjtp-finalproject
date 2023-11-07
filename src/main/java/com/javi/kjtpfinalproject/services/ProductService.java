package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(String productID);
    ProductDTO addNewProduct(ProductDTO newProduct);
    void updateProductById(String productID, ProductDTO newData);
    void deleteById(String productID);
    Product findProduct(String productID);
}
