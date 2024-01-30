package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;
import com.javi.kjtpfinalproject.model.ProductCsvRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "checkoutDetails", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Product productDtoToProduct(ProductDTO newProduct);
    ProductDTO productToProductDto(Product product);
    ProductDTO productCsvRecordToProductDto(ProductCsvRecord product);

}
