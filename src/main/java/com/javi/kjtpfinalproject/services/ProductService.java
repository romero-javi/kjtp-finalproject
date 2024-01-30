package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDTO> findAll(Integer pageNumber, Integer pageSize);
    ProductDTO findById(String productID);
    ProductDTO addNewProduct(ProductDTO newProduct);
    void updateProductById(String productID, ProductDTO newData);
    void deleteById(String productID);
    Product findProduct(String productID);
}
