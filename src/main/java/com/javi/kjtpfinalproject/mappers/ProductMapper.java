package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(target = "checkoutDetail", ignore = true)
    Product productDtoToProduct(ProductDTO newProduct);
    ProductDTO productToProductDto(Product product);
}
