package com.javi.kjtpfinalproject.product.service;

import com.javi.kjtpfinalproject.product.dto.ProductRegistrationDto;
import com.javi.kjtpfinalproject.product.dto.ProductResponseDto;
import com.javi.kjtpfinalproject.product.entities.Product;
import com.javi.kjtpfinalproject.product.mappers.ProductMapper;
import com.javi.kjtpfinalproject.product.repository.ProductRepository;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class ProductDataAccessService implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto findById(String productID) {
        isValidUUID(productID);

        return productMapper.productToProductResponseDto(
                productRepository.findById(UUID.fromString(productID)).orElseThrow(
                        () -> new NotFoundException("Product with id '%s' not found".formatted(productID))
                )
        );
    }

    @Override
    public ProductResponseDto addNewProduct(ProductRegistrationDto newProduct) {
        return productMapper.productToProductResponseDto(
               productRepository.save(
                       productMapper.productRegistrationDtoToProduct(newProduct)
               )
        );
    }

    @Override
    public void updateProductById(String productID, ProductRegistrationDto newData) {
        isValidUUID(productID);

        Product product = productRepository.findById(UUID.fromString(productID)).orElseThrow(
                () -> new NotFoundException("Product with id '%s' not found".formatted(productID))
        );

        product.setName(newData.name());
        product.setPrice(newData.price());
        product.setBrand(newData.brand());
        product.setAvailableQuantity(newData.availableQuantity());

        productRepository.save(product);
    }

    @Override
    public void deleteById(String productID) {
        isValidUUID(productID);

        productRepository.deleteById(UUID.fromString(productID));
    }
}
