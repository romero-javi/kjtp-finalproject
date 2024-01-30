package com.javi.kjtpfinalproject.repositories;

import com.javi.kjtpfinalproject.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .name("Product 1")
                .brand("Brand 1")
                .price(BigDecimal.valueOf(50.5))
                .quantity(4)
                .version(1L)
                .build();
    }

    @DisplayName("Testing count of the repository")
    @Test
    void testCount() {
        Long countBefore = productRepository.count();
        productRepository.save(testProduct);
        Long countAfter = productRepository.count();

        assertThat(countBefore, is(0L));
        assertThat(countAfter, is(1L));
    }

    @DisplayName("Testing saving product")
    @Test
    void testSaveProduct() {
        Product savedProduct = productRepository.save(testProduct);

        assertThat(productRepository.count(), is(1L));
        assertThat(savedProduct, notNullValue());
        assertThat(savedProduct.getProductId(), notNullValue());
        assertThat(savedProduct.getName(), is(testProduct.getName()));
        assertThat(savedProduct.getBrand(), is(testProduct.getBrand()));
        assertThat(savedProduct.getPrice(), is(testProduct.getPrice()));
        assertThat(savedProduct.getQuantity(), is(testProduct.getQuantity()));
        assertThat(savedProduct.getVersion(), is(testProduct.getVersion()));
    }

    @Test
    void testDeleteProduct() {
        productRepository.save(testProduct);
        Long countBeforeDeletion = productRepository.count();
        productRepository.delete(testProduct);
        Long countAfterDeletion = productRepository.count();

        assertThat(countBeforeDeletion, is(1L));
        assertThat(countAfterDeletion, is(0L));
    }

    @DisplayName("Testing saving product")
    @Test
    void testUpdateProduct() {
        String newName = "Product 2";
        String newBrand = "Brand 2";
        BigDecimal newPrice = BigDecimal.valueOf(25.45);
        Integer newQuantity = 2;

        Product savedProduct = productRepository.save(testProduct);
        savedProduct.setName(newName);
        savedProduct.setBrand(newBrand);
        savedProduct.setPrice(newPrice);
        savedProduct.setQuantity(newQuantity);

        Product updatedproduct = productRepository.save(savedProduct);

        assertThat(productRepository.count(), is(1L));
        assertThat(updatedproduct, notNullValue());
        assertThat(updatedproduct.getProductId(), notNullValue());
        assertThat(updatedproduct.getName(), is(newName));
        assertThat(updatedproduct.getBrand(), is(newBrand));
        assertThat(updatedproduct.getPrice(), is(newPrice));
        assertThat(updatedproduct.getQuantity(), is(newQuantity));
        assertThat(updatedproduct.getVersion(), is(2L));
    }
}