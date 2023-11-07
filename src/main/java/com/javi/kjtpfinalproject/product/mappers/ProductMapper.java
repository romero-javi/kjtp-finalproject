package com.javi.kjtpfinalproject.product.mappers;

import com.javi.kjtpfinalproject.product.dto.ProductRegistrationDto;
import com.javi.kjtpfinalproject.product.dto.ProductResponseDto;
import com.javi.kjtpfinalproject.product.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    Product productRegistrationDtoToProduct(ProductRegistrationDto newProduct);
    ProductResponseDto productToProductResponseDto(Product product);
}
