package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;
import com.javi.kjtpfinalproject.mappers.ProductMapper;
import com.javi.kjtpfinalproject.repositories.ProductRepository;
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
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .toList();
    }

    @Override
    public ProductDTO findById(String productID) {
        isValidUUID(productID);

        return productMapper.productToProductDto(
                findProduct(productID)
        );
    }

    @Override
    public ProductDTO addNewProduct(ProductDTO newProduct) {
        Product product = productMapper.productDtoToProduct(newProduct);
        product.setVersion(1L);

        return productMapper.productToProductDto(
               productRepository.save(product)
        );
    }

    @Override
    public void updateProductById(String productID, ProductDTO newData) {
        isValidUUID(productID);

        Product product = findProduct(productID);

        product.setName(newData.name());
        product.setPrice(newData.price());
        product.setBrand(newData.brand());
        product.setQuantity(newData.quantity());

        productRepository.save(product);
    }

    @Override
    public void deleteById(String productID) {
        isValidUUID(productID);

        productRepository.deleteById(UUID.fromString(productID));
    }

    @Override
    public Product findProduct(String productID) {
        isValidUUID(productID);
        return productRepository.findById(UUID.fromString(productID)).orElseThrow(
                () -> new NotFoundException("Product with id '%s' not found".formatted(productID))
        );
    }

}
