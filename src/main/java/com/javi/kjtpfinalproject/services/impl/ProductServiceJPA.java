package com.javi.kjtpfinalproject.services.impl;

import com.javi.kjtpfinalproject.dto.product.ProductDTO;
import com.javi.kjtpfinalproject.entities.Product;
import com.javi.kjtpfinalproject.mappers.ProductMapper;
import com.javi.kjtpfinalproject.repositories.ProductRepository;
import com.javi.kjtpfinalproject.services.ProductService;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class ProductServiceJPA implements ProductService {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> findAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        return productRepository.findAll(pageRequest).map(productMapper::productToProductDto);
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

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else queryPageNumber = DEFAULT_PAGE;

        if(pageSize == null || pageSize <= 0) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else if (pageSize > 1000){
            queryPageSize = 1000;
        } else queryPageSize = pageSize;

        return PageRequest.of(queryPageNumber, queryPageSize);
    }
}
